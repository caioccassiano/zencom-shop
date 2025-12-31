package com.example.zencom.zencom_shop.modules.inventory.application.dtos.output;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public record InventoryItemResultDTO(
        ProductId productId,
        int availableQuantity,
        int reservedQuantity
) {
}
