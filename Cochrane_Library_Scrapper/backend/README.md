# Backend - Cochrane Library Crawler

## Overview
The backend is a Spring Boot application responsible for scraping, processing, and serving Cochrane Reviews through a REST API. Upon initialization, it scrapes titles and links in a suggestions page from the the Cochrane Library review suggestions page. It also scrapes topics from said title links from the Cochrane Library and stores them based on search inputs from the frontend search bar.

## Tech Stack
- **Java 17+**
- **Spring Boot 3+**
- **Maven** (Build Tool)
- **JSoup** (Web Scraping Library)
- **Jackson** (JSON Processing)

## Project Structure
```
├── backend/                     # Backend (Spring Boot application)
│   ├── logs/                     # Backend logs directory 
│	│   ├── backend.log           	# runtime information
│	│   ├── spring.log				# spring boot information
│   ├── src/                     # Java source code
│   │   ├── main/java/iseek/        # Java package containing backend logic
│   │   │   ├── Application.java         # Main Spring Boot entry point
│   │   │   ├── CochraneScraper.java     # Scrapes reviews from Cochrane Library
│   │   │   ├── DataStorage.java         # Stores scraped reviews (for testing)
│   │   │   ├── ReviewController.java    # REST API endpoints for frontend
│   │   │   ├── ReviewMetadata.java      # Data model for review storage
│   │   │   ├── ReviewService.java       # Handles backend business logic
│   │   │   ├── TopicSuggestionService.java # Fetches available search topics
│   │   │
│   │   ├── main/resources/       # Configuration & Data
│   │   │   ├── application.properties    # Spring Boot configuration file
│   │   │   ├── cochrane_reviews.json     # (for testing) Stored reviews data
│   │   │   ├── static/                   # Static storage for review output
│   │   │   │   ├── cochrane_reviews.txt  # Dynamically generated reviews list
│   │   │   │   ├── suggestions.txt       # List of topics for frontend search
│   │
│   ├── target/                   # Compiled Java binaries (post install)
│   ├── pom.xml                   # Maven build file for dependencies
```
## Setup Instructions
### 1. Prerequisites
Ensure you have:
- **Java 17+** installed (`java -version`)
- **Maven** installed (`mvn -version`)

### 2. Building the Project
Navigate to the backend directory:
```linux, cmd, powershell
cd backend
mvn clean install
```
This will compile the Java code, resolve dependencies, and prepare the application.

### 3. Running the Backend
Start the Spring Boot server:
```linux cmd, powershell
mvn spring-boot:run
```
- The backend will be available at **http://localhost:8080**.
- API endpoint for reviews: **`GET /api/reviews`**

### 4. Logs & Debugging
Logs are stored in the `logs/` directory.
To tail logs:
```linux
tail -f logs/application.log
```

### 5. Stopping the Backend
Press **Ctrl + C** to stop the server.
Or manually stop the process:
```linux
kill $(lsof -ti :8080)
```
```cmd
for /f "tokens=5" %a in ('netstat -aon ^| findstr :8080') do taskkill /PID %a /F

```
```powerShell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 8080).OwningProcess
```
## API Endpoints
| Method | Endpoint        | Description |
|--------|----------------|-------------|
| GET    | /api/reviews   | Fetch all Cochrane Reviews |
| GET    | /api/suggestions | Get available topics |

## Additional Resources
- [Spring Boot Documentation](https://spring.io/projects/spring-boot)
- [JSoup Documentation](https://jsoup.org/)
- [Maven Documentation](https://maven.apache.org/)

