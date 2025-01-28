// Main class

package com.iseek;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class Application {
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}
/*
 * How to run
 * Using Maven:
 * Navigate to the backend directory and use the following command:
 * bash
 * mvn spring-boot:run
 * 
 * Directly from the IDE:
 * If you're using an IDE like IntelliJ IDEA or Eclipse:
 * Locate the Application.java file.
 * Right-click the file and select Run 'Application.main()'.
 * 
 * Generate a JAR File If you prefer to run it as a standalone application:
 * 
 * Package the application:
 * bash
 * mvn package
 * 
 * Run the generated JAR file:
 * bash
 * java -jar target/cochrane-backend-1.0-SNAPSHOT.jar
 */