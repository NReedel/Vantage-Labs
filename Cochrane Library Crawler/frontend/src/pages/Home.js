// Might not need
// fetch reviews from the backend.
useEffect(() => {
	fetch("http://localhost:8080/api/reviews")
	  .then(response => response.json())
	  .then(data => setReviews(data))
	  .catch(error => console.error("Error fetching reviews:", error));
  }, []);