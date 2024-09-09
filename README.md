# Golden Raspberry Awards API

## Dependencies:
- JDK 21
- Gradle
- H2
- Spring Boot

## Configurations in application.properties
- app.movie.file
  - location CSV file list movies and producers

## Endpoints:
- [GET] http://localhost:8080/

## H2 Database:
Database running in memmory
Endpoint to acess database data: http://localhost:8080/h2-console

## How to run application
```
./gradlew clean bootRun
```

## How to run tests
```
./gradlew clean test
```
