package com.example.zencom.zencom_shop.modules.payments.domain.exceptions;

public class InvalidInputException extends RuntimeException {
    public InvalidInputException(String message) {
        super(message);
    }
}
