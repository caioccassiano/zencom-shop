package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class OrderItemsCannotBeNullException extends RuntimeException {
    public OrderItemsCannotBeNullException() {
        super("Must have at least one OrderItem");
    }
}
