package com.example.zencom.zencom_shop.modules.shared.ids;

import java.util.UUID;

public final class ProductId {
    private final UUID id;

    public ProductId(UUID id) {
        if   (id == null) {
            throw new IllegalArgumentException("id cannot be null");
        }
        this.id = id;
    }

    public static ProductId newId() {
        return new ProductId(UUID.randomUUID());
    }
    public static ProductId from(String raw) {
        try {
            return new ProductId(UUID.fromString(raw));
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("ProductId invalid");
        }
    }

    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof ProductId)) return false;
        ProductId other = (ProductId) o;
        return id.equals(other.id);
    }

    @Override
    public int hashCode() {
        return id.hashCode();
    }

    public String asString() {
        return id.toString();
    }

    public UUID getId() {
        return id;
    }
}
