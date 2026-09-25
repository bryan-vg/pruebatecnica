package com.example.pruebatecnica.exception;

import java.time.Instant;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final String BAD_REQUEST = "BAD_REQUEST";
    @ExceptionHandler(ResponseStatusException.class)//404 Not Found
    public ResponseEntity<ErrorResponse> handleResourceNotFound(ResponseStatusException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "NOT_FOUND",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(RemoteApiCallException.class)//502 Bad Gateway
    public ResponseEntity<ErrorResponse> handleExternalApiError(RemoteApiCallException ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_GATEWAY.value(),
                "BAD_GATEWAY",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_GATEWAY).body(error);
    }

    @ExceptionHandler(Exception.class)// HTTP 500
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.INTERNAL_SERVER_ERROR.value(),
                "INTERNAL_SERVER_ERROR",
                ex.getMessage(),
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(error);
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)// HTTP 400
    public ResponseEntity<ErrorResponse> handleTypeMismatch(MethodArgumentTypeMismatchException ex) {
        String paramName = ex.getName();
        Object invalidValue = ex.getValue();
        String requiredType = ex.getRequiredType() != null ? ex.getRequiredType().getSimpleName() : "valid number";
        String customMessage = String.format("Invalid value '%s' for parameter '%s'. Expected type: %s", 
                invalidValue, paramName, requiredType);

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                BAD_REQUEST,
                customMessage,
                Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(IllegalArgumentException.class) // HTTP 400
    public ResponseEntity<ErrorResponse> handleIllegalArgument(IllegalArgumentException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            BAD_REQUEST,
            ex.getMessage(),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(HttpMessageNotReadableException.class) // HTTP 400
    public ResponseEntity<ErrorResponse> handlerInvalidTextValue(HttpMessageNotReadableException ex){        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            BAD_REQUEST,
            String.format("Malformed JSON request or invalid data types: %s", ex.getMessage()),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class) // HTTP 400
    public ResponseEntity<ErrorResponse> handlerMissingRequiredValue(MethodArgumentNotValidException ex){
        String missingFieldsErrorText = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(field -> field.getField() + ": " + field.getDefaultMessage())
            .collect(Collectors.joining(", "));
        
        ErrorResponse error = new ErrorResponse(
            HttpStatus.BAD_REQUEST.value(),
            BAD_REQUEST,
            missingFieldsErrorText,
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(NoResourceFoundException.class) // HTTP 400-404
    public ResponseEntity<ErrorResponse> handleNoResourceFound(NoResourceFoundException ex) {
        ErrorResponse error = new ErrorResponse(
            HttpStatus.NOT_FOUND.value(),
            "NOT_FOUND",
            "The requested endpoint or path parameter is missing: " + ex.getResourcePath(),
            Instant.now()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    public record ErrorResponse(// error strucutre sent to clients
            int status,
            String error,
            String message,
            Instant timestamp
    ) {}
}
