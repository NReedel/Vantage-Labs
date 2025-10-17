/* 
 * Review List Component 
 * Renders a list of reviews dynamically.
 * Implements infinite scrolling to load more reviews as the user scrolls down.
 * Uses the Intersection Observer API to detect when the user reaches the end of the list.
 */

import ReviewItem from "./ReviewItem";
import React, { useEffect, useState, useRef, useCallback } from "react";
import "../styles.css";

// Initial number of reviews to display on load
const INITIAL_LOAD = 10;

// Number of reviews to load each time the user reaches the bottom
const LOAD_MORE = 10;

// Delay before loading more reviews (in milliseconds)
const LOAD_DELAY = 1000;

function ReviewList({ reviews, selectedTopic, loading, onClearTopic }) {
    // State for currently displayed reviews
    const [displayedReviews, setDisplayedReviews] = useState([]);
    // State to track the number of loaded reviews
    const [loadedCount, setLoadedCount] = useState(INITIAL_LOAD);
    // State to manage loading indicator for additional reviews
    const [isLoadingMore, setIsLoadingMore] = useState(false);
    // Ref to keep track of the observer instance
    const observer = useRef(null);

    // Effect to update the displayed reviews when a new topic is selected or new reviews are fetched
    useEffect(() => {
        if (Array.isArray(reviews) && reviews.length > 0) {
            setDisplayedReviews(reviews.slice(0, INITIAL_LOAD));
            setLoadedCount(INITIAL_LOAD);
        }
    }, [reviews]);

    // Function to load more reviews when the user reaches the bottom of the list
    const loadMoreReviews = useCallback(() => {
        if (loadedCount < reviews.length) {
            setIsLoadingMore(true);
            setTimeout(() => {
                const nextCount = Math.min(loadedCount + LOAD_MORE, reviews.length);
                setDisplayedReviews(reviews.slice(0, nextCount));
                setLoadedCount(nextCount);
                setIsLoadingMore(false);
            }, LOAD_DELAY);
        }
    }, [loadedCount, reviews]);

    // Intersection Observer callback to detect when the last review is visible
    const lastReviewRef = useCallback(
        (node) => {
            if (loading || isLoadingMore) return;
            if (observer.current) observer.current.disconnect();
            observer.current = new IntersectionObserver((entries) => {
                if (entries[0].isIntersecting && loadedCount < reviews.length) {
                    loadMoreReviews();
                }
            });
            if (node) observer.current.observe(node);
        },
        [loading, isLoadingMore, loadedCount, reviews.length, loadMoreReviews]
    );

    return (
        <div>
            {loading ? (
                // Display loading message while fetching reviews
                <p><span className="bold-text">&emsp; Topics: </span>Loading...</p>
            ) : reviews.length === 0 ? (
                // Display message when no reviews are found
                <p><span className="bold-text"></span></p>
            ) : (
                <>
                    {/* Display selected topic with a clear button */}
                    <p>
                        <span className="bold-text">&emsp; Topics: </span>
                        {selectedTopic && (
                            <span className="select-topic">
                                {selectedTopic}
                                <button className="clear-btn" onClick={onClearTopic}>x</button>
                            </span>
                        )}
                    </p>

                    {/* Show the number of matching Cochrane reviews */}
                    <p>
                        <span className="bold-text">&emsp; {reviews.length}</span> Cochrane Reviews matching <span className="bold-text">{selectedTopic}</span>
                    </p>
                    <br></br>

                    {/* Review List */}
                    <ul className="review-list">
                        {displayedReviews.map((review, index) => (
                            <li className="review-item" key={index} ref={index === displayedReviews.length - 1 ? lastReviewRef : null}>
                                <ReviewItem review={review} />
                            </li>
                        ))}
                    </ul>

                    {/* Show loading message when fetching more reviews */}
                    {isLoadingMore && <p className="loading-more">Loading more reviews...</p>}
                </>
            )}
        </div>
    );
}

export default ReviewList;
