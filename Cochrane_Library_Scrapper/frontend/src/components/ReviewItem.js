/* 
 * Review Item Component 
 * Displays a single review entry with title, author, and other metadata.
 */

import React from "react";
import "./../styles.css";

function ReviewItem({ review }) {
    return (
        <div>
            <a href={review.url} className="review-title">
                {review.title}
            </a>
            <p className="review-authors">{review.authors}</p>
            <p className="review-date">{review.date}</p>
        </div>
    );
}

export default ReviewItem;
