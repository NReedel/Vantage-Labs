/**
 * Service class responsible for managing and processing Cochrane review data.
 * This class fetches and stores review metadata, using suggestions.txt for title refrence and URL. 
 * This also has pagnation features for scrapping multiple pages
 */

package iseek;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.*;
import java.util.*;

@Service
public class ReviewService {

	private static final String SUGGESTIONS_FILE = "src/main/resources/static/suggestions.txt";
	private static final String REVIEWS_FILE = "src/main/resources/static/cochrane_reviews.txt";

	/** Main entry point: fetches all reviews for a topic */
	public List<ReviewMetadata> fetchReviewsByTopic(String topicName) {
		String topicUrl = getTopicUrl(topicName);

		if (topicUrl == null) {
			System.err.println("No matching topic URL found for: " + topicName);
			return List.of();
		}

		System.out.println("Fetching all pages for topic: " + topicName);
		System.out.println("Base URL: " + topicUrl);

		List<ReviewMetadata> allReviews = new ArrayList<>();

		try {
			int currentPage = 1;
			boolean hasMorePages = true;

			while (hasMorePages) {
				String pagedUrl = topicUrl;

				// If the URL doesn't already contain "&cur=", add it
				if (!pagedUrl.contains("cur=")) {
					if (pagedUrl.contains("?")) {
						pagedUrl += "&cur=" + currentPage;
					} else {
						pagedUrl += "?cur=" + currentPage;
					}
				} else {
					// Replace existing page number
					pagedUrl = pagedUrl.replaceAll("cur=\\d+", "cur=" + currentPage);
				}

				System.out.println("Scraping page " + currentPage + ": " + pagedUrl);

				List<ReviewMetadata> pageReviews = scrapeReviews(pagedUrl, topicName);

				if (pageReviews.isEmpty()) {
					System.out.println("No more results found after page " + (currentPage - 1));
					hasMorePages = false;
				} else {
					allReviews.addAll(pageReviews);
					currentPage++;
				}

				// Avoid hammering the server
				Thread.sleep(1500);
			}

			if (!allReviews.isEmpty()) {
				writeReviewsToFile(allReviews);
				System.out.println("Saved " + allReviews.size() + " total reviews for " + topicName);
			} else {
				System.out.println("No reviews found for topic: " + topicName);
			}

		} catch (Exception e) {
			System.err.println("Error while fetching paginated reviews: " + e.getMessage());
		}

		return allReviews;
	}

	/** Looks up the topic URL from suggestions.txt */
	public String getTopicUrl(String topicName) {
		try {
			return Files.lines(Paths.get(SUGGESTIONS_FILE))
					.map(String::trim)
					.filter(line -> line.contains("|"))
					.map(line -> line.split("\\|", 2))
					.filter(parts -> parts.length == 2 && parts[0].trim().equalsIgnoreCase(topicName))
					.map(parts -> parts[1].trim())
					.findFirst()
					.orElse(null);
		} catch (IOException e) {
			System.err.println("Error reading suggestions.txt: " + e.getMessage());
			return null;
		}
	}

	/** Scrapes one page of reviews */
	private List<ReviewMetadata> scrapeReviews(String pageUrl, String topicName) {
		List<ReviewMetadata> reviews = new ArrayList<>();

		try {
			Document doc = Jsoup.connect(pageUrl)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64)")
					.timeout(15000)
					.get();

			Elements reviewElements = doc.select(".search-results-item");

			for (Element reviewElement : reviewElements) {
				String title = reviewElement.select(".result-title a").text();
				String url = reviewElement.select(".result-title a").attr("abs:href");
				String authors = reviewElement.select(".search-result-authors").text();
				String date = reviewElement.select(".search-result-date div").text();

				if (!title.isEmpty()) {
					reviews.add(new ReviewMetadata(url, topicName, title, authors, date));
					System.out.println("✅ Review: " + title);
				}
			}

		} catch (IOException e) {
			System.err.println("❌ Error fetching page: " + e.getMessage());
		}

		return reviews;
	}

	/** Writes all reviews to cochrane_reviews.txt */
	private void writeReviewsToFile(List<ReviewMetadata> reviews) {
		try (BufferedWriter writer = Files.newBufferedWriter(Paths.get(REVIEWS_FILE))) {
			for (ReviewMetadata review : reviews) {
				writer.write(String.join(" | ",
						review.getTopic(),
						review.getTitle(),
						review.getAuthors(),
						review.getDate(),
						review.getUrl()));
				writer.newLine();
			}
			System.out.println("cochrane_reviews.txt written successfully!");
		} catch (IOException e) {
			System.err.println("Error writing cochrane_reviews.txt: " + e.getMessage());
		}
	}
}
