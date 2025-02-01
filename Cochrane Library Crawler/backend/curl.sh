curl -X POST http://localhost:8080/reviews \
     -H "Content-Type: application/json" \
     -d '[
        {
            "url": "https://example.com/review1",
            "title": "Review Title 1",
            "topic": "Health",
            "author": "John Doe",
            "date": "2025-01-29"
        }
     ]'

# Test a POST Request (Save Reviews)
# If your API allows saving reviews, use this