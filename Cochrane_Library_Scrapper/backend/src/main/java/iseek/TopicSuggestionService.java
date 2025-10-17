/**
 * This service generates and provides topic suggestions for search functionality.
 * It loads topics dynamically to assist in user input auto-suggestions.
 */

package iseek;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class TopicSuggestionService {

	private static final String TOPICS_URL = "https://www.cochranelibrary.com/cdsr/reviews/topics";
	private static final String FILE_PATH = "src/main/resources/static/suggestions.txt";
	
	// scrapes cochrane library topics to generate sugggestions.txt //
	@PostConstruct
	public void generateSuggestions() {
		try {
			// Connect with proper User-Agent + timeout
			Document doc = Jsoup.connect(TOPICS_URL)
					.userAgent("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 Chrome/100.0")
					.timeout(10000)
					.get();

			Elements topicElements = doc.select("li.browse-by-list-item a");

			File file = new File(FILE_PATH);
			file.getParentFile().mkdirs();

			try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, false))) {
				for (Element topic : topicElements) {
					String topicName = topic.text().trim();
					String topicUrl = topic.absUrl("href").trim();

					// Write both name and URL separated by a pipe
					writer.write(topicName + "|" + topicUrl);
					writer.newLine();
				}

			}

			System.out.println("Suggestions file generated at " + FILE_PATH);

		} catch (IOException e) {
			System.err.println("Error generating topic suggestions: " + e.getMessage());
		}
	}
}
