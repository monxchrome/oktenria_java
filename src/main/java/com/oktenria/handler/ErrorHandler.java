package com.oktenria.handler;

import com.oktenria.dto.ErrorDto;
import jakarta.validation.ConstraintViolationException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ErrorHandler {

    @ExceptionHandler({DataIntegrityViolationException.class})
    public ResponseEntity<ErrorDto> handleDatabaseException(DataIntegrityViolationException exception) {
        String details = exception.getCause().getCause().getMessage();

        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }

    @ExceptionHandler({MethodArgumentNotValidException.class})
    public ResponseEntity<ErrorDto> handleNotValidException(MethodArgumentNotValidException exception) {
        String details = exception.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }

    @ExceptionHandler({ConstraintViolationException.class})
    public ResponseEntity<ErrorDto> handleNotValidException(ConstraintViolationException exception) {
        String details = exception.getMessage();

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ErrorDto.builder()
                        .timestamp(System.currentTimeMillis())
                        .details(details)
                        .build());
    }
}
