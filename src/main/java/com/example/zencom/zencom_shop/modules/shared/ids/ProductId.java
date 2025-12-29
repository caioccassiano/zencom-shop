package com.example.zencom.zencom_shop.modules.shared.ids;

import java.util.UUID;

public final class ProductId {
    private final String id;

    public ProductId(String id) {
        if   (id == null || id.isBlank()) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID().toString());
    }

    public String getId() {
        return id;
    }
}
