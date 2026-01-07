package com.example.zencom.zencom_shop.modules.cart.application.exception;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(String message) {
        super(message);
    }
}
