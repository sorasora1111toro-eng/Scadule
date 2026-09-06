# SCADULE

A simple calendar-based schedule application built with Java and Spring Boot.

## Features

- Add a schedule with a title and date
- View scheduled items directly in a monthly calendar grid
- Move between previous and next months
- Lightweight in-memory storage for demo/prototype use

## Tech stack

- Java 26
- Spring Boot 4.1.0
- Thymeleaf
- Gradle

## Run locally

On Windows:

```powershell
cd C:\Scadule
./gradlew.bat bootRun
```

Then open:

```text
http://localhost:35676/
```

## Project structure

- `src/main/java/com/example/demo` - Java application code
- `src/main/resources/templates` - HTML/Thymeleaf views
- `src/main/resources/application.properties` - app configuration

## Notes

This is a simple prototype. The schedule list is stored in memory, not a database.

## GitHub sharing

1. Create a new repository on GitHub
2. Run the following from this project directory:

```bash
git remote add origin <your-github-repository-url>
git branch -M main
git push -u origin main
```

I changed file more comfortable.
