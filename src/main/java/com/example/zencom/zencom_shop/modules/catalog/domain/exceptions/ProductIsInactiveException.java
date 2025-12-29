package com.example.zencom.zencom_shop.modules.catalog.domain.exceptions;

public class ProductIsInactiveException extends RuntimeException {
    public ProductIsInactiveException() {
        super("Product it not active");
    }
}
