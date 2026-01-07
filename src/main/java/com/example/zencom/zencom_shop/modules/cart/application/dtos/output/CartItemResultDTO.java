package com.example.zencom.zencom_shop.modules.cart.application.dtos.output;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record CartItemResultDTO (
        UUID productId,
        int quantity,
        BigDecimal price,
        Instant addedAt
){}


