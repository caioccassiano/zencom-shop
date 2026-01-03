package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class DisccountInvalidException extends RuntimeException {
    public DisccountInvalidException() {
        super("Discount must be greater than zero.");
    }
}
