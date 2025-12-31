package com.example.zencom.zencom_shop.modules.inventory.application.exceptions;

public class InventoryItemNotFoundException extends RuntimeException {
    public InventoryItemNotFoundException() {
        super("Product not found");
    }
}
