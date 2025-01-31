package com.iseek;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class ReviewService {

	private static final String API_URL = "https://www.cochranelibrary.com/cdsr/reviews/topics";

	public String fetchReviews() throws IOException {
		CloseableHttpClient httpClient = HttpClients.createDefault();
		HttpGet request = new HttpGet(API_URL);

		// Add headers to the request
		request.addHeader("User-Agent", "Mozilla/5.0");
		request.addHeader("Accept", "application/json");
		// Uncomment the Authorization header if needed
		// request.addHeader("Authorization", "Bearer YOUR_API_KEY");

		try (CloseableHttpResponse response = httpClient.execute(request)) {
			HttpEntity entity = response.getEntity();
			if (entity != null) {
				try (InputStream inputStream = entity.getContent();
						BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
					return reader.lines().collect(Collectors.joining("\n"));
				}
			}
		}

		return null;
	}
}