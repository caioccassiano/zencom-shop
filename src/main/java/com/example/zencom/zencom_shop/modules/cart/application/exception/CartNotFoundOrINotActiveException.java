package com.example.zencom.zencom_shop.modules.cart.application.exception;

public class CartNotFoundOrINotActiveException extends RuntimeException {
    public CartNotFoundOrINotActiveException(String message) {
        super(message);
    }
}
