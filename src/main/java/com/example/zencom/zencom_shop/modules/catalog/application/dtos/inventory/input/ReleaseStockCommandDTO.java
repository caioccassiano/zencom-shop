package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input;

import java.util.UUID;

public record ReleaseStockCommandDTO(
        UUID productId,
        int quantity
) {
}
