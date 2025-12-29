package com.example.zencom.zencom_shop.modules.catalog.application.exceptions;

public class ProductDoesNotExistException extends RuntimeException {
    public ProductDoesNotExistException() {
        super("Product doesn't exist");
    }
}
