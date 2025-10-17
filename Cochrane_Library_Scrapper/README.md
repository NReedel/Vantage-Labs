# Cochrane Library Crawler
By  : Nathanaial Reedel


## Overview
The Cochrane Library Crawler is a full-stack web application that allows users to search and browse Cochrane Reviews efficiently. It consists of:
- **Backend**: A Spring Boot REST API that scrapes and serves review data.
- **Frontend**: A React-based html interface that scrapes and displays reviews dynamically.
- **Run Script**: A shell & batch script (run.sh/run.bat) to automate launching both backend and 	frontend.

## Project Structure
```
Cochrane Library Crawler/
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
│
├── frontend/                      # Frontend (React application)
│   ├── node_modules/              # Installed npm packages (post install)
│   ├── public/                    # Static public files
│   │   ├── index.html             # Main HTML template
│   │
│   ├── src/                       # React source code
│   │   ├── api/                   # API functions for fetching backend data
│   │   │   ├── fetchReviews.js    # Handles fetching reviews from backend
│   │   │
│   │   ├── components/            # React components
│   │   │   ├── ReviewItem.js      # Displays a single review
│   │   │   ├── ReviewList.js      # Displays multiple reviews dynamically
│   │   │   ├── SearchBox.js       # Search bar for filtering topics
│   │   │
│   │   ├── pages/                 # Page components
│   │   │   ├── Home.js            # Main homepage containing the search
│   │   │
│   │   ├── styles.css             # Global styles
│   │   ├── App.js                 # Main React entry point
│   │   ├── index.js               # Renders React app
│   │
│   ├── package.json               # Project dependencies and scripts
│   ├── package-lock.json          # Dependency lock file
│── run.sh                       # Script to start backend & frontend (linux based)
│── run.bat                      # Script to start backend & frontend (windows based)
```

## Setup & Usage
### 1. Prerequisites
Ensure the following are installed:
- **Java 17+** (for backend)
- **Maven** (for backend)
- **Node.js & npm** (for frontend)

### 2. Running the Application
Execute app from terminal located at the project root folder using the following commands based on the following OS's :
**Linux/Mac**
	./run.sh
**Windows**
	run.bat

This will mainly:
1. Start the **backend (Spring Boot)** on port `8080`.
2. Start the **frontend (React)** on port `3000`.
**Installs, checks, and self corrects are also peroformed**

### 3. Understanding the `run` Script
The `run` script:
- **Frees ports 8080 & 3000** (kills previous processes if necessary)
- **Builds the backend** using Maven
- **Starts the backend in the background** and waits until it’s running
- **Sets up the frontend**, installs dependencies if missing, and starts React

### 4. Stopping the Application
Press **Ctrl + C** to stop the frontend.
Run:
```linux
kill $(lsof -ti :8080)
kill $(lsof -ti :3000)
```
to stop any remaining backend/frontend processes.

## Additional Documentation
- **[Backend README](backend/README.md)** - Details backend setup further.
- **[Frontend README](frontend/README.md)** - Covers frontend configuration.

