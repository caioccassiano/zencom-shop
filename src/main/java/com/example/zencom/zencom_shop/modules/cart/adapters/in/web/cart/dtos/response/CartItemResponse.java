package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CartItemResponse(
        UUID productId,
        int quantity,
        BigDecimal price,
        Instant addedAt
) {
}
