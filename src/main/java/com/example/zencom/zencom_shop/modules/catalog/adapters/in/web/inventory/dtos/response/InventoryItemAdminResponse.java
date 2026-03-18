package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.response;

import java.util.UUID;

public record InventoryItemAdminResponse(

        UUID productId,
        String productName,
        int availableQuantity,
        int reservedQuantity,
        int totalQuantity
) {}
