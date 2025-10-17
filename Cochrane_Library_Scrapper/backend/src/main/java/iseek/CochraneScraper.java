/**
 * This class handles web scraping for the Cochrane Library.
 * It fetches review metadata dynamically from the Cochrane website.
 */

package iseek;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class CochraneScraper {
	private static final String FILE_PATH = "src/main/resources/static/cochrane_reviews.txt";

	public static void saveReviewsToFile(List<ReviewMetadata> reviews) {
		File file = new File(FILE_PATH);
		// dir check
		file.getParentFile().mkdirs();

		try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
			for (ReviewMetadata review : reviews) {
				writer.write(review.toString());
				writer.newLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
