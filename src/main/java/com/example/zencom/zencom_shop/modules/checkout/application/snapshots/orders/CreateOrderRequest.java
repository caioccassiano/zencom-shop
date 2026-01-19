package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderRequest(
        UUID costumerId,
        List<CreateOrderItem> items,
        UUID reservationId,
        BigDecimal totalAmount
) {
}
