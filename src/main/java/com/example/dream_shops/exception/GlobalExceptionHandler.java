package com.example.dream_shops.exception;

import com.example.dream_shops.response.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.nio.file.AccessDeniedException;

import static org.springframework.http.HttpStatus.UNAUTHORIZED;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<ApiResponse> handleResourceNotFoundException(AccessDeniedException e) {
        String message = "You do not have permission to perform this action";
        return ResponseEntity.status(UNAUTHORIZED).body(ApiResponse.builder()
                .message(message)
                .build());
    }
}
