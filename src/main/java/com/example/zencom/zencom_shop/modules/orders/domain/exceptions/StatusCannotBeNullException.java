package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class StatusCannotBeNullException extends RuntimeException {
    public StatusCannotBeNullException() {
        super("Status can't be null");
    }
}
