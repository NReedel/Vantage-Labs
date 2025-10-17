package iseek;

/**
 * REST Controller that handles API requests for Cochrane reviews.
 * Provides endpoints to fetch review data dynamically.
 */

import org.springframework.web.bind.annotation.*;
import org.springframework.beans.factory.annotation.Autowired;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api")
public class ReviewController {

	private static final String SUGGESTIONS_FILE = "src/main/resources/static/suggestions.txt";

	@Autowired
	private ReviewService reviewService;

	@GetMapping("/reviews")
	public List<ReviewMetadata> getReviews(@RequestParam(name = "topic", required = false) String topic) {
		if (topic == null || topic.isBlank()) {
			return List.of(); // no topic provided
		}

		try {
			// Pass the topic name into the service (service resolves URL and scrapes)
			List<ReviewMetadata> reviews = reviewService.fetchReviewsByTopic(topic.trim());
			return reviews;
		} catch (Exception e) {
			System.err.println("Error fetching reviews for topic '" + topic + "': " + e.getMessage());
			return List.of();
		}
	}

	/**
	 * GET /api/suggestions
	 * Returns titles only (left of the pipe)
	 */
	@GetMapping("/suggestions")
	public List<String> getSuggestions() {
		try {
			return Files.lines(Paths.get(SUGGESTIONS_FILE))
					.map(String::trim)
					.filter(line -> !line.isEmpty() && line.contains("|"))
					.map(line -> line.split("\\|", 2)[0].trim()) // return only topic titles
					.collect(Collectors.toList());
		} catch (IOException e) {
			System.err.println("Error reading suggestions.txt: " + e.getMessage());
			return List.of(); // empty on error
		}
	}
}
