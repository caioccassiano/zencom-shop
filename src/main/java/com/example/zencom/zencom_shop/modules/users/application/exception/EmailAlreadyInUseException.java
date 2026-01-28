package com.example.zencom.zencom_shop.modules.users.application.exception;

public class EmailAlreadyInUseException extends RuntimeException {
    public EmailAlreadyInUseException() {
        super("Email already used");
    }
}
