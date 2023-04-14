package com.client.ws.rasmooplus.exception.handler;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.client.ws.rasmooplus.dto.error.ErrorResponseDto;
import com.client.ws.rasmooplus.exception.BadRequestException;
import com.client.ws.rasmooplus.exception.NotFoundException;

@RestControllerAdvice
public class ResourceHandler {
    
    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponseDto> notFoundException(NotFoundException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
            .message(exception.getMessage())
            .httpStatus(HttpStatus.NOT_FOUND)
            .statusCode(HttpStatus.NOT_FOUND.value())
            .build();
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponseDto);
    }

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<ErrorResponseDto> badRequestException(BadRequestException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
            .message(exception.getMessage())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponseDto> badRequestException(MethodArgumentNotValidException exception) {
        Map<String, String> messages = new HashMap<>();

        exception.getBindingResult().getAllErrors().forEach(error -> {
            String field = ((FieldError) error).getField();
            String defaultMessage = error.getDefaultMessage();
            messages.put(field, defaultMessage);
        });

        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
            .message(Arrays.toString(messages.entrySet().toArray()))
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<ErrorResponseDto> badRequestException(DataIntegrityViolationException exception) {
        ErrorResponseDto errorResponseDto = ErrorResponseDto.builder()
            .message(exception.getMessage())
            .httpStatus(HttpStatus.BAD_REQUEST)
            .statusCode(HttpStatus.BAD_REQUEST.value())
            .build();
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponseDto);
    }
    

}
