package com.iseek;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CochraneScraper {
	// If no JSON API is available and HTML scraping is the only option

	public List<ReviewMetadata> fetchReviews(String url) throws IOException {
		List<ReviewMetadata> reviews = new ArrayList<>();
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(url);

		// Adding headers
		request.addHeader("User-Agent", "Mozilla/5.0");
		request.addHeader("Accept", "text/html");

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				// Parse HTML content
				Document doc = Jsoup.parse(entity.getContent(), "UTF-8", url);
				Elements reviewElements = doc.select(".review-item"); // Adjust selector based on Cochrane's structure

				for (Element reviewElement : reviewElements) {
					ReviewMetadata review = new ReviewMetadata();
					review.setUrl(reviewElement.select("a").attr("href"));
					review.setTitle(reviewElement.select(".review-title").text());
					review.setTopic(reviewElement.select(".review-topic").text());
					review.setAuthor(reviewElement.select(".review-author").text());
					review.setDate(reviewElement.select(".review-date").text());
					reviews.add(review);
				}
			}
		}
		return reviews;
	}
	// If Cochrane provides a reliable API that returns JSON

	// Alternative implementation for fetching reviews as JSON (commented-out)
	// public List<ReviewMetadata> fetchReviewsAsJson(String url) throws IOException
	// {
	// CloseableHttpClient httpClient = HttpClients.createDefault();
	// HttpGet request = new HttpGet(url);

	// // Adding headers
	// request.addHeader("User-Agent", "Mozilla/5.0");
	// request.addHeader("Accept", "application/json");

	// try (CloseableHttpResponse response = httpClient.execute(request)) {
	// HttpEntity entity = response.getEntity();
	// if (entity != null) {
	// // Parse JSON content
	// ObjectMapper mapper = new ObjectMapper();
	// return mapper.readValue(entity.getContent(),
	// mapper.getTypeFactory().constructCollectionType(List.class,
	// ReviewMetadata.class));
	// }
	// }
	// return new ArrayList<>();
	// }
}