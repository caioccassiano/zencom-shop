package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class TotalCannotBeNullException extends RuntimeException {
    public TotalCannotBeNullException() {
        super("Total cannot be null.");
    }
}
