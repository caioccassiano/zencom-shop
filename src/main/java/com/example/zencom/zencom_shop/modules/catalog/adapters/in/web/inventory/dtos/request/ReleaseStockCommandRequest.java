package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request;

import jakarta.validation.constraints.NotNull;

public record ReleaseStockCommandRequest (
        @NotNull
        int quantity
) {}
