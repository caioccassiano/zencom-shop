package com.example.zencom.zencom_shop.modules.cart.domain.exceptions;

public class CartIsEmptyException extends RuntimeException {
    public CartIsEmptyException(String message) {
        super(message);
    }
}
