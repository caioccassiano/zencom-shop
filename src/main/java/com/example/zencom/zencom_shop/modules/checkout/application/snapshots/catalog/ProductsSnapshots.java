package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog;

import java.util.Map;
import java.util.UUID;

public record ProductsSnapshots(
        Map<UUID, ProductSnapshot> products
) {
    public ProductSnapshot getProducts(UUID productId) {
        return products.get(productId);
    }
}
