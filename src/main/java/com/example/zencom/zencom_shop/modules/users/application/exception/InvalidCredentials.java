package com.example.zencom.zencom_shop.modules.users.application.exception;

public class InvalidCredentials extends RuntimeException {
    public InvalidCredentials(String message) {
        super(message);
    }
}
