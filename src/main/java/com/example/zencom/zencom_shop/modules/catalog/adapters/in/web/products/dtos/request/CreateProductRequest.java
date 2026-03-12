package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.products.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.math.BigDecimal;

public record CreateProductRequest(
        @JsonProperty("name")
        @NotBlank
        String name,

        @JsonProperty("description")
        @NotBlank
        String description,

        @JsonProperty("price")
        @NotNull
        @Positive
        BigDecimal price

) {
}
