package com.example.zencom.zencom_shop.modules.shared.security.exceptions;

public class UnauthenticatedException extends RuntimeException {
    public UnauthenticatedException(String message) {
        super("Unauthenticated");
    }
}
