/* 
 * API Handler: Fetch Reviews 
 * This function retrieves Cochrane reviews from the backend API.
 * NOTE: backend expects the parameter name "topic"
 */

export async function fetchReviews(query = "") {
  try {
    const encodedQuery = encodeURIComponent(query);
    // Use 'topic' parameter to match backend ReviewController
    const response = await fetch(`http://localhost:8080/api/reviews?topic=${encodedQuery}`);
    if (!response.ok) {
      console.error("fetchReviews: HTTP error", response.status);
      return [];
    }
    const data = await response.json();

    if (Array.isArray(data) && data.length > 0) {
      console.log(`Found ${data.length} reviews for topic: "${query}"`);
      return data;
    } else {
      console.log(`No reviews found for topic: "${query}".`);
      return [];
    }
  } catch (error) {
    console.error("Error fetching reviews:", error);
    return [];
  }
}