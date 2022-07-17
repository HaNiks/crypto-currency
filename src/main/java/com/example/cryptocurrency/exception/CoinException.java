package com.example.cryptocurrency.exception;

import com.example.cryptocurrency.util.CoinErrorResponse;
import com.example.cryptocurrency.util.CoinNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;

@ControllerAdvice
public class CoinException {
    @ExceptionHandler
    private ResponseEntity<CoinErrorResponse> handlerException(CoinNotFoundException exception) {
        CoinErrorResponse response = new CoinErrorResponse("Coin with this name wasn't found",
                LocalDateTime.now());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
}
