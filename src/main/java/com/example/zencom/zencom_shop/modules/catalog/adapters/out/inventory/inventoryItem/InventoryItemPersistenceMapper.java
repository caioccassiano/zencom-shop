package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.inventoryItem;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;

public class InventoryItemPersistenceMapper {
    private InventoryItemPersistenceMapper() {}
    public static InventoryItem toDomain(InventoryItemJpaEntity entity){
        return InventoryItem.restore(
                entity.getProductId(),
                entity.getAvailableQuantity(),
                entity.getReservedQuantity(),
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
    public static InventoryItemJpaEntity toEntity(InventoryItem inventoryItem){
        InventoryItemJpaEntity entity = new InventoryItemJpaEntity();
        entity.setProductId(inventoryItem.getProductId().getId());
        entity.setAvailableQuantity(inventoryItem.getAvailableQuantity());
        entity.setReservedQuantity(inventoryItem.getReservedQuantity());
        entity.setCreatedAt(inventoryItem.getCreatedAt());
        entity.setUpdatedAt(inventoryItem.getUpdatedAt());
        return entity;
    }
}
