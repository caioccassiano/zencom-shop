package com.example.zencom.zencom_shop.modules.inventory.domain.exceptions;

public class InvalidStockQuantityException extends RuntimeException {
    public InvalidStockQuantityException() {

        super("Quantity must be positive");
    }
}
