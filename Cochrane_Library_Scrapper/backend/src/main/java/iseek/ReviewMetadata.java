/**
 * Data model representing the metadata of a Cochrane review.
 * This class defines the fields required to store review information.
 */

package iseek;

public class ReviewMetadata {
	private String url;
	private String topic;
	private String title;
	private String authors;
	private String date;

	// Constructor
	public ReviewMetadata(String url, String topic, String title, String authors, String date) {
		this.url = url;
		this.topic = topic;
		this.title = title;
		this.authors = authors;
		this.date = date;
	}

	// Getters
	public String getUrl() {
		return url;
	}

	public String getTopic() {
		return topic;
	}

	public String getTitle() {
		return title;
	}

	public String getAuthors() {
		return authors;
	} 
	
	public String getDate() {
		return date;
	}

	// Setters
	public void setUrl(String url) {
		this.url = url;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setAuthors(String authors) {
		this.authors = authors;
	}

	public void setDate(String date) {
		this.date = date;
	}
}