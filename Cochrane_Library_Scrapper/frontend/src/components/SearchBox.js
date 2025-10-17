/* 
 * Search Box Component 
 * Provides an input field for users to search for reviews by topic.
 * Implements auto-suggestions and debounced searching.
 * Supports clearing search results via an external prop.
 */

import React, { useState, useEffect, useRef, useCallback, useMemo } from "react";
import "../styles.css";

const SearchBox = ({ onSearch, externalClear, searchBoxRef }) => {
    // State for user input
    const [query, setQuery] = useState(sessionStorage.getItem("searchQuery") || "");
    // State to store all available suggestions
    const [suggestions, setSuggestions] = useState([]);
    // State for filtering suggestions based on input
    const [filteredSuggestions, setFilteredSuggestions] = useState([]);
    // State to control visibility of the dropdown menu
    const [showDropdown, setShowDropdown] = useState(false);
    // Local reference to the search box (used if an external ref isn't provided)
    const localSearchBoxRef = useRef(null);

    // Fetch topic suggestions from the backend when the component mounts
    useEffect(() => {
        fetch("/api/suggestions")
            .then((response) => response.json())
            .then((data) => setSuggestions(data))
            .catch((error) => console.error("Error fetching suggestions:", error));
    }, []);

    // Save query to sessionStorage and trigger search when input changes
    useEffect(() => {
        sessionStorage.setItem("searchQuery", query);
        if (query.length > 0) {
            debouncedSearch(query);
        }
    }, [query]);

    // Perform an initial search if a query is already set
    useEffect(() => {
        if (query.length > 0) {
            onSearch(query);
        }
    }, []);

    // Listen for `externalClear` prop changes to clear the search box
    useEffect(() => {
        console.log("externalClear changed:", externalClear); // Debug log
        if (externalClear) {
            clearSearch();
        }
    }, [externalClear]);

    /**
     * Debounce function to delay execution of search requests.
     * Prevents excessive API calls while typing.
     */
    const debounce = (func, delay) => {
        let timer;
        return (...args) => {
            clearTimeout(timer);
            timer = setTimeout(() => func(...args), delay);
        };
    };

    // Debounced search function to minimize API requests while typing
    const debouncedSearch = useCallback(debounce(onSearch, 300), [onSearch]);

    /**
     * Filters available suggestions based on user input.
     * Uses `useMemo` to optimize performance and prevent unnecessary recalculations.
     */
    const filteredResults = useMemo(() => {
        if (query.length === 0) return suggestions;
        return suggestions.filter(topic => topic.toLowerCase().startsWith(query.toLowerCase()));
    }, [query, suggestions]);

    // Update filtered suggestions whenever the input changes
    useEffect(() => {
        setFilteredSuggestions(filteredResults);
        setShowDropdown(query.length === 0 || filteredResults.length > 0);
    }, [filteredResults, query]);

    /**
     * Handles input changes in the search box.
     * Updates the query state based on user input.
     */
    const handleChange = (e) => {
        setQuery(e.target.value);
    };

    /**
     * Handles when a user selects a suggested topic from the dropdown.
     * Updates the input value and triggers a search.
     */
    const handleSelect = (topic) => {
        setQuery(topic);
        setShowDropdown(false);
        onSearch(topic);
    };

    /**
     * Handles input focus event.
     * If no query is entered, show all available suggestions.
     */
    const handleFocus = () => {
        if (query.length === 0) {
            setFilteredSuggestions(suggestions);
            setShowDropdown(suggestions.length > 0);
        }
    };

    /**
     * Detects clicks outside the search box and hides the dropdown.
     */
    const handleClickOutside = (event) => {
        if (localSearchBoxRef.current && !localSearchBoxRef.current.contains(event.target)) {
            setShowDropdown(false);
        }
    };

    // 🔹 Add event listener to detect clicks outside the search box
    useEffect(() => {
        document.addEventListener("mousedown", handleClickOutside);
        return () => {
            document.removeEventListener("mousedown", handleClickOutside);
        };
    }, []);

    /**
     * Clears the search box and results.
     * Also blurs (removes focus from) the input field.
     */
    const clearSearch = () => {
        console.log("clearSearch() triggered! Clearing search and results."); // Debug log
        setQuery("");
        onSearch(""); // Clear search results

        const inputRef = searchBoxRef || localSearchBoxRef;
        if (inputRef.current) {
            inputRef.current.blur(); // Remove focus from search box
        }
    };
    
    return (
        <div className="search-box-container" ref={localSearchBoxRef}>
            <div className="search-box-wrapper">
                <input
                    type="text"
                    className="search-box"
                    placeholder="Search Cochrane Reviews..."
                    value={query}
                    onChange={handleChange}
                    onFocus={handleFocus}
                    ref={searchBoxRef || localSearchBoxRef} // Use external ref if available
                />
                {showDropdown && filteredSuggestions.length > 0 && (
                    <div className="dropdown-menu">
                        {filteredSuggestions.map((topic, index) => (
                            <div 
                                key={index} 
                                className="dropdown-item"
                                onMouseDown={() => handleSelect(topic)}
                            >
                                {topic}
                            </div>
                        ))}
                    </div>
                )}
            </div>
        </div>
    );
};

export default SearchBox;
