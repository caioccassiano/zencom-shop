package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderItem(
        UUID productId,
        int quantity,
        BigDecimal unitPrice
) {
}
