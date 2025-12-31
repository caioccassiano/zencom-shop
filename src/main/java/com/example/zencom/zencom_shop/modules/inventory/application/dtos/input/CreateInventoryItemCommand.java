package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public record CreateInventoryItemCommand(
        ProductId productId
) {
}
