package com.example.zencom.zencom_shop.modules.catalog.application.mappers;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;

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
