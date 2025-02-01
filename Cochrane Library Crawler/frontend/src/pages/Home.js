

import React, { useState, useEffect } from "react";
import "./../styles.css"; // Ensure styles are correctly linked

const Home = () => {
    const [reviews, setReviews] = useState([]); // Stores reviews
    const [filteredReviews, setFilteredReviews] = useState([]); // Stores filtered reviews based on search
    const [searchTerm, setSearchTerm] = useState(""); // User input in search box
    const [suggestions, setSuggestions] = useState([]); // Auto-suggestions
    const [visibleCount, setVisibleCount] = useState(10); // Controls lazy loading

    // Fetch reviews from backend API
    useEffect(() => {
        fetch("http://localhost:8080/api/reviews")
            .then((response) => response.json())
            .then((data) => {
                setReviews(data);
                setFilteredReviews(data);
            })
            .catch((error) => console.error("Error fetching reviews:", error));
    }, []);

    // Handle search input
	const [searchQuery, setSearchQuery] = useState(""); // State to store input value
	const handleSearch = (event) => {
		const query = event.target.value;
		setSearchQuery(query); // Update state to allow typing
	
		if (!query) {
			setFilteredReviews(reviews); // Reset list if input is empty
			return;
		}
	
		// Ensure query is a string before using toLowerCase()
		const lowerCaseQuery = typeof query === "string" ? query.toLowerCase() : "";
	
		const filtered = reviews.filter(review =>
			review.title && review.title.toLowerCase().includes(lowerCaseQuery)
		);
	
		setFilteredReviews(filtered);
	};
	// const handleSearch = (event) => {
	// 	const query = event.target.value;
	
	// 	if (!query) {
	// 		setFilteredReviews(reviews); // Reset list if input is empty
	// 		return;
	// 	}
	
	// 	// Ensure query is a string before using toLowerCase()
	// 	const lowerCaseQuery = typeof query === "string" ? query.toLowerCase() : "";
	
	// 	const filtered = reviews.filter(review =>
	// 		review.title && review.title.toLowerCase().includes(lowerCaseQuery)
	// 	);
	
	// 	setFilteredReviews(filtered);
	// };

    // Handle suggestion click
    const handleSuggestionClick = (topic) => {
        setSearchTerm(topic);
        setFilteredReviews(reviews.filter((review) => review.topic === topic));
        setSuggestions([]);
    };

    // Load more reviews on scroll
    const handleScroll = () => {
        if (window.innerHeight + document.documentElement.scrollTop >= document.documentElement.offsetHeight) {
            setVisibleCount((prev) => prev + 10); // Load 10 more
        }
    };
	// fetch reviews from the backend.
    useEffect(() => {
        window.addEventListener("scroll", handleScroll);
        return () => window.removeEventListener("scroll", handleScroll);
    }, []);

    return (
        <div className="container">
            {/* Search Box */}
            <input
                type="text"
                className="search-box"
                placeholder="Search Cochrane Topics..."
                value={searchTerm}
                onChange={handleSearch}
            />
            
            {/* Auto-suggestions */}
            {suggestions.length > 0 && (
                <ul className="suggestions">
                    {suggestions.map((suggestion, index) => (
                        <li key={index} onClick={() => handleSuggestionClick(suggestion.topic)}>
                            {suggestion.topic}
                        </li>
                    ))}
                </ul>
            )}

            {/* Reviews List */}
            <div className="reviews-list">
                {filteredReviews.slice(0, visibleCount).map((review, index) => (
                    <div key={index} className="review">
                        <a href={review.url} target="_blank" rel="noopener noreferrer" className="review-title">
                            {review.title}
                        </a>
                        <p className="review-author">By {review.author}</p>
                        <p className="review-date">{review.date}</p>
                        <p className="review-topic">Topic: {review.topic}</p>
                    </div>
                ))}
            </div>
        </div>
    );
};

export default Home;
