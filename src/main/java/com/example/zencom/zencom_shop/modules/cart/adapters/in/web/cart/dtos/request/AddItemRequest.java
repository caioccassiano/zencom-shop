package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.util.UUID;

public record AddItemRequest(
        @NotNull @JsonProperty("product_id") UUID productId,
        @NotNull @Positive int quantity
) {
}
