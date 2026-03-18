package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output;

import java.util.UUID;

public record InventoryItemResultAdminDTO(
        UUID productId,
        String productName,
        int availableQuantity,
        int reservedQuantity,
        int totalQuantity
) {
}
