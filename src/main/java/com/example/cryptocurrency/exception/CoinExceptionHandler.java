package com.example.cryptocurrency.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CoinExceptionHandler {
    @ExceptionHandler
    private ResponseEntity<CoinErrorResponse> handleException(CoinNotFoundException exception) {
        CoinErrorResponse response = new CoinErrorResponse("Coin with this name wasn't found",
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
