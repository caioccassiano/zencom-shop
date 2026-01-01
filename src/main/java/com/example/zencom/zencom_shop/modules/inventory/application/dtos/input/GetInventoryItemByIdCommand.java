package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input;

import java.util.UUID;

public record GetInventoryItemByIdCommand(
        String productId
) {
    public GetInventoryItemByIdCommand{
        if(productId == null||productId.isBlank()){
            throw new IllegalArgumentException("productId cannot be null");
        }
    }
}
