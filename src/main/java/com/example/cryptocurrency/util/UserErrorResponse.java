package com.example.cryptocurrency.util;

import java.time.LocalDateTime;

public class UserErrorResponse {

    private String message;
    private LocalDateTime time;

    public UserErrorResponse(String message, LocalDateTime time) {
        this.message = message;
        this.time = time;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public LocalDateTime getTime() {
        return time;
    }

    public void setTime(LocalDateTime time) {
        this.time = time;
    }
}
