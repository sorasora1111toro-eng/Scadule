# SCADULE

A simple calendar-based schedule application with a Java Swing desktop app and a Spring Boot web version.

## Features

- Add a schedule with a title and date
- View scheduled items directly in a monthly calendar grid
- Move between previous and next months
- Local H2 database for persistent schedule storage

## Tech stack

- Java 25
- Swing (desktop UI)
- Spring Boot 4.1.2
- Thymeleaf
- Gradle

## Run locally

On Windows:

```powershell
.\gradlew.bat runDesktop
```

Run the command from the project directory. Alternatively, double-click `ScaduleLauncher.vbs` or `ScaduleLauncher.bat`.

## Project structure

- `src/main/java/com/example/demo` - Java application code
- `src/main/resources/templates` - HTML/Thymeleaf views
- `src/main/resources/application.properties` - app configuration

## Notes

The desktop app stores schedules in a local H2 database under the user's `ScaduleDesktop` folder.

## GitHub sharing

1. Create a new repository on GitHub
2. Run the following from this project directory:

```bash
git remote add origin <your-github-repository-url>
git branch -M main
git push -u origin main
```

I changed file more comfortable.
