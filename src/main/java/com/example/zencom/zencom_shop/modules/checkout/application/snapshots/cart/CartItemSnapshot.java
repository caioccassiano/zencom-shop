package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CartItemSnapshot(
        UUID productId,
        int quantity,
        BigDecimal price,
        Instant addedAt
) {
}
