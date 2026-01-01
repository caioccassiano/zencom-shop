package com.example.zencom.zencom_shop.modules.inventory.application.mappers;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;

public final class InventoryItemResultMapper {

    private InventoryItemResultMapper(){}

    public static InventoryItemResultDTO toDTO(InventoryItem inventoryItem){
        return new InventoryItemResultDTO(
            inventoryItem.getProductId().getId(),
            inventoryItem.getAvailableQuantity(),
            inventoryItem.getReservedQuantity(),
            inventoryItem.getAvailableQuantity() + inventoryItem.getReservedQuantity());
    }


}
