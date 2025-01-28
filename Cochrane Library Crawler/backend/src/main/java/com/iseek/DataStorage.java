package com.iseek;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class DataStorage {
	private static final String OUTPUT_FILE = "cochrane_reviews.json";

	public void saveToFile(List<ReviewMetadata> reviews) throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.writerWithDefaultPrettyPrinter().writeValue(new File(OUTPUT_FILE), reviews);
	}
}

// package com.iseek;

// import com.fasterxml.jackson.databind.ObjectMapper;
// import java.io.File;
// // import java.io.IOException;
// import java.util.List;

// public class DataStorage {
// private static final String DATA_FILE =
// "src/main/resources/cochrane_reviews.json";
// private final ObjectMapper objectMapper = new ObjectMapper();

// public List<ReviewMetadata> loadReviews() throws Exception {
// return objectMapper.readValue(new File(DATA_FILE),
// objectMapper.getTypeFactory().constructCollectionType(List.class,
// ReviewMetadata.class));
// }

// public void saveReviews(List<ReviewMetadata> reviews) throws Exception {
// objectMapper.writeValue(new File(DATA_FILE), reviews);
// }
// }