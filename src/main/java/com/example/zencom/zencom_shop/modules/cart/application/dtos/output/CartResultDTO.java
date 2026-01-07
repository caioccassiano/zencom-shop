package com.example.zencom.zencom_shop.modules.cart.application.dtos.output;

import com.example.zencom.zencom_shop.modules.cart.domain.entities.CartItem;
import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record CartResultDTO(
        UUID cartId,
        UUID userId,
        CartStatus status,
        List<CartItemResultDTO> items,
        Instant createdAt,
        Instant updatedAt
) {
}
