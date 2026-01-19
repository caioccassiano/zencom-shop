package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.catalog;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSnapshot(
        UUID productId,
        String name,
        BigDecimal price,
        boolean active
) {
}
