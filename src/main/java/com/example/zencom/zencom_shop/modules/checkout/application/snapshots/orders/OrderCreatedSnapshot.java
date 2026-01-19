package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders;

import java.util.UUID;

public record OrderCreatedSnapshot(
        UUID orderId,
        OrderStatus status
) {
}
