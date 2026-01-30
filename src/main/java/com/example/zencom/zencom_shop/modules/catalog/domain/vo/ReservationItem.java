package com.example.zencom.zencom_shop.modules.catalog.domain.vo;

import java.util.UUID;

public record ReservationItem(
        UUID productId,
        int quantity
) {
    public ReservationItem{
        if (productId == null) throw new IllegalArgumentException("productId cannot be null");
        if (quantity <= 0) throw new IllegalArgumentException("quantity cannot be greater than zero");
    }
}
