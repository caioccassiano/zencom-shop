package com.example.zencom.zencom_shop.modules.inventory.domain.exceptions;

public class InsufficientReservedStockException extends RuntimeException {
    public InsufficientReservedStockException() {
        super("Not enough reserved stock");
    }
}
