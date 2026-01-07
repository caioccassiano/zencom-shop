package com.example.zencom.zencom_shop.modules.cart.domain.exceptions;

public class InvalidCartItemException extends RuntimeException {
    public InvalidCartItemException(String message) {
        super(message);
    }
}
