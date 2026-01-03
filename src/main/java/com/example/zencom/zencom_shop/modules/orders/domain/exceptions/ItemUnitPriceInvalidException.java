package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class ItemUnitPriceInvalidException extends RuntimeException {
    public ItemUnitPriceInvalidException() {
        super("Unit price must be greater than zero");
    }
}
