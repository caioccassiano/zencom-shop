package com.example.zencom.zencom_shop.modules.orders.domain.exceptions;

public class OrderAlreadyFinalizedException extends RuntimeException {
    public OrderAlreadyFinalizedException() {
        super("Order is already finalized.");
    }
}
