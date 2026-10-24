@echo off
cd /d "%~dp0"
call gradlew.bat runDesktop
if errorlevel 1 (
    echo.
    echo Failed to start Scadule.
    echo Make sure Java 25 or newer is installed, then try again.
    pause
)