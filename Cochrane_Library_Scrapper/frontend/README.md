# Frontend - Cochrane Library Crawler

## Overview
The frontend is a React-based web interface for browsing Cochrane Reviews. It provides a search bar with automatic suggestions, dynamic filtering, and an infinite scroll feature to display reviews. It depends and communicates with the backed for full functionality. 

## Tech Stack
- **React 18+**
- **JavaScript (ES6+)**
- **Fetch API** (for API calls)
- **CSS Modules** (for styling)

## Setup Instructions
### 1. Prerequisites
Ensure you have:
- **Node.js 16+** installed (`node -v`)
- **npm** installed (`npm -v`)

### 2. Installing Dependencies
Navigate to the frontend directory:
```linux, cmd, powershell
cd frontend
npm install
```
This will install all required packages listed in `package.json`.

### 3. Running the Frontend
Start the React development server:
```linux, cmd, powershell
npm start
```
- The frontend will be available at **http://localhost:3000**.
- Ensure the backend is running before starting the frontend.

### 4. Stopping the Frontend
Press **Ctrl + C** to stop the React server.
Or manually stop the process:
```linux
kill $(lsof -ti :3000)
```
```cmd
for /f "tokens=5" %a in ('netstat -aon ^| findstr :3000') do taskkill /PID %a /F
```
```powershell
Stop-Process -Id (Get-NetTCPConnection -LocalPort 3000).OwningProcess
```


## Project Structure (basic)
```
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
**node_modules is later installed in root**

## Additional Resources
- [React Documentation](https://react.dev/)
- [MDN Fetch API](https://developer.mozilla.org/en-US/docs/Web/API/Fetch_API)

