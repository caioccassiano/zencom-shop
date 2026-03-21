package com.example.zencom.zencom_shop.modules.cart.adapters.in.web.cart.dtos.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

public record UpdateItemQuantityRequest(
        @NotNull @Min(0) int quantity
) {
}
