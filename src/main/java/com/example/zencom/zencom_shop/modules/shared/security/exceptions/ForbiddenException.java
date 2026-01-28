package com.example.zencom.zencom_shop.modules.shared.security.exceptions;

public class ForbiddenException extends RuntimeException {
    public ForbiddenException(String message) {
        super(message);
    }
}
