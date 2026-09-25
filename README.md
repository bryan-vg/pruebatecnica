# Pet API Technical Test

Simple Spring Boot REST API that integrates with the Swagger Petstore external API.

## Technologies

- Java 17
- Spring Boot 3.2.7
- Gradle (Groovy DSL)
- OkHttp
- Jackson
- Jakarta Validation
- Lombok
- JUnit / Spring Boot Test

## Features

The application exposes two main endpoints:

### Get a pet by ID

```http
GET /api/pet/{petId}
```

Example:

```http
GET /api/pet/314
```

This endpoint calls the external Swagger Petstore API and returns the pet information.

### Create a pet

```http
POST /api/pet
```

Example request body:

```json
{
  "id": 314,
  "status": "available",
  "name": "Firulais"
}
```

Example response:

```json
{
  "transactionId": "generated-uuid",
  "dateCreated": "2026-09-25T09:30:00.000000",
  "status": true,
  "name": "Firulais"
}
```

## External API

The project consumes the Swagger Petstore API:

```text
https://petstore.swagger.io/v2/pet
```

OkHttp is used as the HTTP client, with connection, read, and write timeouts configured through a reusable Spring bean.

## Validation and error handling

The API includes:

- Validation for required request fields.
- Positive-number validation for pet IDs.
- Global exception handling with `@RestControllerAdvice`.
- `404 Not Found` handling when a pet does not exist.
- `400 Bad Request` responses for invalid input.
- `502 Bad Gateway` responses for unexpected external API failures.

## Running the project

From the project root:

```bash
./gradlew bootRun
```

On Windows Git Bash:

```bash
./gradlew.bat bootRun
```

The application runs on Spring Boot's default port:

```text
http://localhost:8080
```

## Build

Run:

```bash
./gradlew clean build
```

or on Windows Git Bash:

```bash
./gradlew.bat clean build
```

## Project structure

```text
src/main/java/com/example/pruebatecnica/
├── apiclients/
│   └── RestClient.java
├── config/
│   └── HttpClientConfig.java
├── controller/
│   └── PetController.java
├── dto/
│   ├── Pet.java
│   ├── PetSaveRequest.java
│   └── PetSaveResponse.java
├── exception/
│   ├── GlobalExceptionHandler.java
│   └── RemoteApiCallException.java
├── service/
│   ├── PetService.java
│   └── impl/
│       └── PetServiceImpl.java
└── PruebatecnicaApplication.java
```
