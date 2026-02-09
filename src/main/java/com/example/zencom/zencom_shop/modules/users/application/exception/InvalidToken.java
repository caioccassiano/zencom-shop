package com.example.zencom.zencom_shop.modules.users.application.exception;

public class InvalidToken extends RuntimeException {
    public InvalidToken(String message) {
        super(message);
    }
}
