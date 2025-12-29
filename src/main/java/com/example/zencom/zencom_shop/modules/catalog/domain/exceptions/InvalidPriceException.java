package com.example.zencom.zencom_shop.modules.catalog.domain.exceptions;

public class InvalidPriceException extends RuntimeException {
    public InvalidPriceException() {
        super("Price must be greater than zero");
    }
}
