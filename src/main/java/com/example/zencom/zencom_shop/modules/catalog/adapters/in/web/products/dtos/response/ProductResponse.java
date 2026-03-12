package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.response;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductResponse(
        UUID productId,
        String name,
        String description,
        BigDecimal price,
        String status,
        Instant createdAt

) {
}

