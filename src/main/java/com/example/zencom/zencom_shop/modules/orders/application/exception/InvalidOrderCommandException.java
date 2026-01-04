package com.example.zencom.zencom_shop.modules.orders.application.exception;

public class InvalidOrderCommandException extends RuntimeException {
    public InvalidOrderCommandException(String message) {
        super(message);
    }
}
