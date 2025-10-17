/**
 * This class is used for testing purposes only.
 * It provides functionality to load and save review data from a local JSON file
 * (cochrane_reviews.json).
 *
 * By default, this class is disabled in the application using
 * the @Profile("test") annotation.
 * To enable it for testing, run the application with the "test" profile:
 *
 * Command:
 * SPRING_PROFILES_ACTIVE=test mvn test
 *
 * This will allow the DataStorage component to be used during tests but keep it
 * disabled in production and development.
 */

package iseek;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.List;

@Profile("test") // Ensures this component is only active in the "test" profile
@Component
public class DataStorage {
	private final ObjectMapper objectMapper = new ObjectMapper();
	private static final String DATA_FILE = "src/main/resources/cochrane_reviews.json"; // Path to the local JSON file

	/**
	 * Loads review data from the local JSON file.
	 * If the file does not exist, it returns an empty list.
	 *
	 * @return List of ReviewMetadata objects
	 * @throws IOException if there is an issue reading the file
	 */
	public List<ReviewMetadata> loadReviews() throws IOException {
		File file = new File(DATA_FILE);
		if (!file.exists()) {
			return List.of(); // Return empty list if file doesn't exist
		}
		return objectMapper.readValue(file,
				objectMapper.getTypeFactory().constructCollectionType(List.class, ReviewMetadata.class));
	}

	/**
	 * Saves review data to the local JSON file.
	 *
	 * @param reviews List of ReviewMetadata objects to save
	 * @throws IOException if there is an issue writing to the file
	 */
	public void saveReviews(List<ReviewMetadata> reviews) throws IOException {
		objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(DATA_FILE), reviews);
	}
}
