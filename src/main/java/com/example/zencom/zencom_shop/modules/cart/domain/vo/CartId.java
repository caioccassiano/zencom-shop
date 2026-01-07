package com.example.zencom.zencom_shop.modules.cart.domain.vo;

import java.util.Objects;
import java.util.UUID;

public final class CartId {

    private final UUID id;

    private CartId(UUID id) {
        this.id = Objects.requireNonNull(id, "CartId cannot be null");
    }

    public static  CartId newId() {
        return new CartId(UUID.randomUUID());
    }

    public static CartId from_UUID(UUID id) {
        return new CartId(id);
    }

    public static CartId from(String cartId) {
        return new CartId(UUID.fromString(cartId));
    }

    public UUID getId() {
        return id;
    }

    public String toString(){
        return id.toString();
    }
}
