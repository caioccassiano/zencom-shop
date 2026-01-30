package com.example.zencom.zencom_shop.modules.catalog.domain.exceptions;

public class InsufficientReservedStockException extends RuntimeException {
    public InsufficientReservedStockException() {
        super("Not enough reserved stock");
    }
}
