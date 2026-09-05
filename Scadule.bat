@echo off
REM Scadule Application Launcher
REM =================================
cd /d "%~dp0"

echo.
echo ========================================
echo   Scadule - Schedule Management App
echo ========================================
echo.
echo [*] Starting application...
echo [*] Java version check...

java -version >nul 2>&1
if errorlevel 1 (
    echo.
    echo [ERROR] Java is not installed or not in PATH
    echo Please install Java and add it to your PATH environment variable.
    echo.
    pause
    exit /b 1
)

REM Start the Spring Boot application in background
echo [*] Launching Scadule...
start javaw -jar build\libs\demo-0.0.1-SNAPSHOT.jar

REM Wait for application to start
echo [*] Waiting for application to start (5 seconds)...
timeout /t 5 /nobreak

REM Open browser
echo [*] Opening browser...
start http://localhost:8080

echo [+] Application started successfully!
echo [*] Access at: http://localhost:8080
echo.
pause
