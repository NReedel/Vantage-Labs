#!/bin/bash

# ========================================
# Cochrane Library Crawler - Run Script
# Starts backend (Spring Boot) and frontend (React).
# Ensures backend/logs is the only log directory.
# ========================================

PROJECT_ROOT="$(cd "$(dirname "$0")" && pwd)"
BACKEND_DIR="$PROJECT_ROOT/backend"
FRONTEND_DIR="$PROJECT_ROOT/frontend"
BACKEND_LOG_DIR="$BACKEND_DIR/logs"
LOG_FILE="$BACKEND_LOG_DIR/backend.log"

mkdir -p "$BACKEND_LOG_DIR"

run_command() {
  echo "🔹 Running: $1"
  eval "$1"
}

free_port() {
  local PORT=$1
  echo "🔹 Checking for existing process on port $PORT..."
  if command -v npx >/dev/null 2>&1; then
    npx kill-port $PORT >/dev/null 2>&1 || true
  elif lsof -i :$PORT >/dev/null 2>&1; then
    echo "⚠️ Port $PORT in use. Stopping..."
    lsof -ti :$PORT | xargs kill -15 2>/dev/null
    sleep 2
  else
    echo "✅ Port $PORT is free."
  fi
}

free_port 8080
free_port 3000

echo "🚀 Starting Backend..."
cd "$BACKEND_DIR" || { echo "❌ Backend directory not found!"; exit 1; }

run_command "mvn clean install" || { echo "❌ Maven build failed!"; exit 1; }

echo "🔹 Creating new backend log file..."
> "$LOG_FILE"

nohup mvn spring-boot:run > "$LOG_FILE" 2>&1 &
BACKEND_PID=$!

echo "⏳ Waiting for backend to start..."
until curl -s http://localhost:8080/api/reviews >/dev/null; do
  sleep 2
  echo "⏳ Still waiting for backend..."
done
echo "✅ Backend is up."

echo "🚀 Starting Frontend..."
cd "$FRONTEND_DIR" || { echo "❌ Frontend directory not found!"; exit 1; }

if command -v nvm >/dev/null 2>&1; then
  run_command "nvm use"
fi

if [ ! -d "node_modules" ]; then
  run_command "npm install"
else
  echo "✅ node_modules found. Skipping npm install."
fi

run_command "npm start"

cleanup() {
  echo "🛑 Stopping backend..."
  kill -15 "$BACKEND_PID" 2>/dev/null || true
  exit 0
}

trap cleanup SIGINT SIGTERM
