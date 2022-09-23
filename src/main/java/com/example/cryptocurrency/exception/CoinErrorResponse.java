package com.example.cryptocurrency.exception;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class CoinErrorResponse {

    private String message;
    private LocalDateTime time;

    public CoinErrorResponse(String message, LocalDateTime time) {
    }
}
