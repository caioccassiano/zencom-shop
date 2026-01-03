package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class ProductIdICannotBeNullException extends RuntimeException {
    public ProductIdICannotBeNullException() {
        super("Product Id cannot be null");
    }
}
