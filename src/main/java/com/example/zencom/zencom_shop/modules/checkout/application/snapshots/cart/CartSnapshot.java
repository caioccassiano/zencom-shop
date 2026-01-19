package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CartSnapshot(
        UUID cartId,
        UUID costumerId,
        List<CartItemSnapshot> items,
        BigDecimal totalAmount,
        Instant createdAt,
        Instant updatedAt
) {
}
