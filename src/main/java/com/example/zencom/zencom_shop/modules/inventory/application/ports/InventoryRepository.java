package com.example.zencom.zencom_shop.modules.inventory.application.ports;

import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.Optional;

public interface InventoryRepository {
    Optional<InventoryItem> findByProductId(ProductId productId);

    boolean existsByProductId(ProductId productId);

    void save(InventoryItem inventoryItem);

}
