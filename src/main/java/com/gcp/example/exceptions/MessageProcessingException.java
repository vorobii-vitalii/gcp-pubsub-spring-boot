package com.gcp.example.exceptions;

public class MessageProcessingException extends RuntimeException {
    public MessageProcessingException() {
    }

    public MessageProcessingException(String message) {
        super(message);
    }
}
