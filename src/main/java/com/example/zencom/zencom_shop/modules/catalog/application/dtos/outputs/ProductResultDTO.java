package com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;
import java.time.Instant;

public record ProductResultDTO(
        ProductId id,
        String name,
        String description,
        BigDecimal price,
        ProductStatus status,
        Instant createdAt,
        Instant updatedAt
) {
}
