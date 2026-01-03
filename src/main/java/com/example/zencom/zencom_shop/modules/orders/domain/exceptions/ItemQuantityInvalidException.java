package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class ItemQuantityInvalidException extends RuntimeException {
    public ItemQuantityInvalidException() {
        super("Quantity must be greater than zero");
    }
}
