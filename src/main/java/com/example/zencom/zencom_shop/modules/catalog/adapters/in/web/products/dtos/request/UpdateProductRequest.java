package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.util.UUID;

public record UpdateProductRequest(

        @Size(min = 3, max = 100)
        String name,

        @Size(max = 250)
        String description,
        @Positive
        BigDecimal price
) {
}
