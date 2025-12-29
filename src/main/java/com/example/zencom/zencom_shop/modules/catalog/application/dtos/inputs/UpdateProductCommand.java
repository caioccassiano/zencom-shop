package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.math.BigDecimal;

public record UpdateProductCommand(
        ProductId id,
        String name,
        String description,
        BigDecimal price
) {
}
