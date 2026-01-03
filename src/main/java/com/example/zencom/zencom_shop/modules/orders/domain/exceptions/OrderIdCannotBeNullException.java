package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class OrderIdCannotBeNullException extends RuntimeException {
    public OrderIdCannotBeNullException() {
        super("OrderId can't be null");
    }
}
