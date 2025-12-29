package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs;

import java.math.BigDecimal;

public record CreateProductCommand(
        String name,
        String description,
        BigDecimal price
) {
}
