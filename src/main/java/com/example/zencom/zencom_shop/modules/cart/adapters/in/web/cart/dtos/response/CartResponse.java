package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.response;

import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CartResponse(
        UUID cartId,
        UUID userId,
        CartStatus status,
        List<CartItemResponse> items,
        Instant createdAt,
        Instant updatedAt
) {
}
