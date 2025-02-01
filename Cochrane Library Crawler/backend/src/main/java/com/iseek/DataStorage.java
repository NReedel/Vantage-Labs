package com.iseek;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataStorage {
	private static final String DATA_FILE = "src/main/resources/cochrane_reviews.json";
	private final ObjectMapper objectMapper = new ObjectMapper();

	public List<ReviewMetadata> loadReviews() throws IOException {
		File file = new File(DATA_FILE);
		if (!file.exists()) {
			return List.of(); // Return empty list if file doesn't exist
		}
		return objectMapper.readValue(file,
				objectMapper.getTypeFactory().constructCollectionType(List.class, ReviewMetadata.class));
	}

	public void saveReviews(List<ReviewMetadata> reviews) throws IOException {
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE), reviews);
	}
}