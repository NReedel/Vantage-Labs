package com.iseek;

import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@RequestMapping("/api/reviews") // API endpoint
@CrossOrigin(origins = "http://localhost:3000") // ✅ Allow frontend requests
public class ReviewController {

    private static final Logger logger = LoggerFactory.getLogger(ReviewController.class);

    @GetMapping
    public String getReviews() {
        try {
            // Load file from classpath
            Resource resource = new ClassPathResource("cochrane_reviews.json");

            // Log the path (this will be helpful for debugging)
            logger.info("Loading review file: {}", resource.getFilename());

            // Read file contents
            InputStream inputStream = resource.getInputStream();
            String content = new String(inputStream.readAllBytes(), StandardCharsets.UTF_8);

            logger.info("Successfully loaded review data");
            return content;
        } catch (IOException e) {
            logger.error("Error reading the review data file", e);
            throw new RuntimeException("Error reading the review data file", e);
        }
    }
}