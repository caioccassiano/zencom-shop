package com.example.zencom.zencom_shop.modules.payments.domain.exceptions;

public class InvalidPaymentStateException extends RuntimeException {
    public InvalidPaymentStateException(String message) {
        super(message);
    }
}
