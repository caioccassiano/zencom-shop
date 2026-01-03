package com.example.zencom.zencom_shop.modules.shared.ids;

import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;

import java.util.UUID;

public final class OrderId {
    private UUID id;

    private OrderId(UUID id) {
        if(id == null) {
            throw new IllegalArgumentException("id cannot be null");
        };
        this.id = id;
    }

    public static OrderId newId() {
        return new OrderId(UUID.randomUUID());
    }
    public static OrderId from (String orderId) {
        try {
            return new OrderId(UUID.fromString(orderId));
        }
        catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(orderId);
        }
    }
    public UUID getId() {
        return id;
    }

    public String asString() {
        return id.toString();
    }



}
