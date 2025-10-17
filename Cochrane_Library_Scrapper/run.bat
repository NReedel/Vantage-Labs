@echo off
REM ========================================
REM Spring Boot + React Run Script for Windows
REM Node-like behavior: kills backend on exit, frees log
REM ========================================

setlocal enabledelayedexpansion

set "BACKEND_DIR=%~dp0backend"
set "FRONTEND_DIR=%~dp0frontend"
set "LOG_DIR=%BACKEND_DIR%\logs"
set "LOG_FILE=%LOG_DIR%\backend.log"

REM -----------------------------
REM Kill old backend/frontend processes
REM -----------------------------
echo 🔹 Cleaning up old processes...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do taskkill /PID %%a /F >nul 2>&1
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :3000') do taskkill /PID %%a /F >nul 2>&1

REM -----------------------------
REM Ensure log directory exists
REM -----------------------------
if not exist "%LOG_DIR%" mkdir "%LOG_DIR%"
echo. > "%LOG_FILE%"

REM -----------------------------
REM Build backend
REM -----------------------------
echo 🚀 Building Backend...
pushd "%BACKEND_DIR%"
call mvn clean install || (
    echo ❌ Maven build failed. Exiting.
    exit /b 1
)
popd

REM -----------------------------
REM Start backend in a new window and capture PID
REM -----------------------------
echo 🚀 Starting Backend (logging to %LOG_FILE%)...
pushd "%BACKEND_DIR%"
start "Backend" cmd /k "mvn spring-boot:run > "%LOG_FILE%" 2>&1"
popd

REM Wait a moment for PID to register
timeout /t 2 >nul

REM -----------------------------
REM Wait for backend to open port 8080
REM -----------------------------
echo ⏳ Waiting for backend to start...
set COUNT=0
:waitloop
powershell -Command "try { (New-Object System.Net.Sockets.TcpClient('localhost',8080)).Close(); exit 0 } catch { exit 1 }"
if errorlevel 1 (
    set /a COUNT+=1
    if %COUNT% GEQ 60 (
        echo ❌ Backend did not start after ~2 minutes. Check "%LOG_FILE%" for details.
        exit /b 1
    )
    timeout /t 2 >nul
    goto waitloop
)
echo ✅ Backend is up and running.

REM -----------------------------
REM Start frontend in a new window
REM -----------------------------
echo 🚀 Starting Frontend...
pushd "%FRONTEND_DIR%"
if not exist "node_modules" (
    echo 🔹 Installing dependencies...
    call npm install
)
start "Frontend" cmd /k "npm start"
popd

echo 🟢 App is running at http://localhost:3000
echo 🔹 Press Ctrl+C to stop both servers.

REM -----------------------------
REM Keep script alive so Ctrl+C works
REM -----------------------------
:loop
timeout /t 5 >nul
goto loop
