package com.example.zencom.zencom_shop.modules.orders.application.exception;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(String message) {
        super(message);
    }
}
