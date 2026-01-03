package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class UserIdCannotBeNullException extends RuntimeException {
    public UserIdCannotBeNullException() {
        super("UserId can't be null");
    }
}
