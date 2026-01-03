package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class ProductNameInvalidException extends RuntimeException {
    public ProductNameInvalidException() {
        super("Product name cannot be empty or blank");
    }
}
