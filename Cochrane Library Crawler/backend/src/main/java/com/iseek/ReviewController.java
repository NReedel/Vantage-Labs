package com.iseek;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

/* Original */
@RestController
public class ReviewController {
    private static final String DATA_FILE = "cochrane_reviews.json";

    @GetMapping("/api/reviews")
    public List<ReviewMetadata> getReviews() throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(Files.readAllBytes(Paths.get(DATA_FILE)),
                mapper.getTypeFactory().constructCollectionType(List.class,
                        ReviewMetadata.class));
    }
}

/*
 * Use the Service in a Controller:
 * Inject this service into a controller
 * to expose the API endpoint.
 */

/*
 * // For ReviewsService.java
 * 
 * @RestController
 * 
 * @RequestMapping("/api/reviews")
 * public class ReviewsController {
 * 
 * private final ReviewsService reviewsService;
 * 
 * public ReviewsController(ReviewsService reviewsService) {
 * this.reviewsService = reviewsService;
 * }
 * 
 * @GetMapping
 * public String getReviews() {
 * return reviewsService.fetchReviews();
 * }
 * }
 * 
 * Test the Endpoint:
 * 
 * Run the application.
 * Access the backend API endpoint at http://localhost:8080/api/reviews (or
 * whatever port you set).
 */