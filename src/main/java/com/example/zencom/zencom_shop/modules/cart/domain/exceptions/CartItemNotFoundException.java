package com.example.zencom.zencom_shop.modules.cart.domain.exceptions;

public class CartItemNotFoundException extends RuntimeException {
    public CartItemNotFoundException(String message) {
        super(message);
    }
}
