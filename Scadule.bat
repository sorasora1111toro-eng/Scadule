@echo off
cd /d "%~dp0"
call gradlew.bat runDesktop
if errorlevel 1 pause
