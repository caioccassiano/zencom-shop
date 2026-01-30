package com.example.zencom.zencom_shop.modules.catalog.domain.exceptions;

public class InsufficientStockException extends RuntimeException {
    public InsufficientStockException() {
        super("Not enough available stock");
    }
}
