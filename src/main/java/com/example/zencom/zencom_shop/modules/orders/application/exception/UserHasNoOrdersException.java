package com.example.zencom.zencom_shop.modules.orders.application.exception;

public class UserHasNoOrdersException extends RuntimeException {
    public UserHasNoOrdersException(String message) {
        super(message);
    }
}
