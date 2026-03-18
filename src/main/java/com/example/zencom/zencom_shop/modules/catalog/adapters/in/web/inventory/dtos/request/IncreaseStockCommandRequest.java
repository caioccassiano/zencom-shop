package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request;

import jakarta.validation.constraints.NotNull;

import java.util.UUID;

public record IncreaseStockCommandRequest(
        @NotNull
        int quantity
) {
}
