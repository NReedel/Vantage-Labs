import React, { useState, useEffect } from "react";
import SearchBox from "../components/SearchBox";
import ReviewList from "../components/ReviewList";
import { fetchReviews } from "../api/fetchReviews";
import "../styles.css";

const Home = () => {
    const [query, setQuery] = useState("");
    const [reviews, setReviews] = useState(null); // Use null to indicate uninitialized state
    const [selectedTopic, setSelectedTopic] = useState("");
    const [loading, setLoading] = useState(false);
    const [externalClear, setExternalClear] = useState(false); // New state for clearing search

    useEffect(() => {
        if (!query) return;

        setLoading(true);
        setReviews(null); // Reset reviews before fetching new ones
        
        fetchReviews(query)
            .then((data) => {
                setReviews(data.length > 0 ? data : []);
                setSelectedTopic(query);
            })
            .catch((error) => {
                console.error("Error fetching reviews:", error);
                setReviews([]); // Set to empty array to prevent undefined errors
            })
            .finally(() => {
                setLoading(false);
            });
    }, [query]);

    // 🔹 New function to clear the selected topic and search box
    const clearSelectedTopic = () => {
        sessionStorage.removeItem("searchQuery"); 
        window.location.reload(); 
       
    };

    return (
        <div>
            {/* Pass externalClear so SearchBox clears itself when toggled */}
            <SearchBox onSearch={setQuery} externalClear={externalClear} />
            <ReviewList 
                reviews={reviews || []} 
                selectedTopic={selectedTopic} 
                loading={loading} 
                onClearTopic={clearSelectedTopic} // Pass clear function
            />
        </div>
    );
};

export default Home;
