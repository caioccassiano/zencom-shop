package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.UUID;

public record InventoryItemResultDTO(
        UUID productId,
        int availableQuantity,
        int reservedQuantity,
        int totalQuantity
) {
}
