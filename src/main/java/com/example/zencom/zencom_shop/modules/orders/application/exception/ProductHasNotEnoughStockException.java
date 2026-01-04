package com.example.zencom.zencom_shop.modules.orders.application.exception;

public class ProductHasNotEnoughStockException extends RuntimeException {
    public ProductHasNotEnoughStockException(String message) {
        super(message);
    }
}
