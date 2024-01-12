package com.example.SW.exceptions;

import java.time.LocalDateTime;

public class ErrorDetail {
    public String error;
    private String details;
    private LocalDateTime timestamp;

    public ErrorDetail(String error, String details, LocalDateTime timestamp) {
        super();
        this.error = error;
        this.details = details;
        this.timestamp = timestamp;
    }

}
