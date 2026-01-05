package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input;

import java.util.UUID;

public record GetInventoryItemByIdCommand(
        UUID productId
) {
    public GetInventoryItemByIdCommand{
        if(productId == null){
            throw new IllegalArgumentException("productId cannot be null");
        }
    }
}
