package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input;

import java.util.UUID;

public record ReleaseStockCommandDTO(
        UUID productId,
        int quantity
) {
}
