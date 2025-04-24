package org.example.cs489project.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.example.cs489project.exception.custom.DuplicateUsernameExcepiton;
import org.example.cs489project.exception.custom.PostNotFoundException;
import org.example.cs489project.exception.custom.UsernameNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.time.Instant;

@RestControllerAdvice
public class GlobalExceptionHandler {
//    @ExceptionHandler(GenreNotFoundException.class)
//    public ResponseEntity<ApiError> genreNotFoundException(GenreNotFoundException e, HttpServletRequest request) {
//        ApiError apiError = new ApiError(
//                Instant.now(),
//            HttpStatus.BAD_REQUEST.value(),
//            HttpStatus.BAD_REQUEST.name(),
//            e.getMessage(),
//            request.getRequestURI()
//        );
//        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
//    }
    @ExceptionHandler(DuplicateUsernameExcepiton.class)
    public  ResponseEntity<ApiError> duplicateUsernameExcepiton(DuplicateUsernameExcepiton ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.BAD_REQUEST.value(),
                HttpStatus.BAD_REQUEST.name(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(apiError);
    }
    @ExceptionHandler(UsernameNotFoundException.class)
    public  ResponseEntity<ApiError> usernameNotFound(UsernameNotFoundException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
    @ExceptionHandler(PostNotFoundException.class)
    public  ResponseEntity<ApiError> postNotFound(PostNotFoundException ex, HttpServletRequest request) {
        ApiError apiError = new ApiError(
                Instant.now(),
                HttpStatus.NOT_FOUND.value(),
                HttpStatus.NOT_FOUND.name(),
                ex.getMessage(),
                request.getRequestURI()
        );
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(apiError);
    }
}