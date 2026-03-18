package com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultAdminDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemRepository {
    Optional<InventoryItem> findByProductId(ProductId productId);

    boolean existsByProductId(ProductId productId);

    void save(InventoryItem inventoryItem);

    List<InventoryItem> findAll();

    //in the adapter must be done a query WHERE availableQuantity > 0
    List<InventoryItem> findAllWithAvailableQuantity();

    List<InventoryItemResultAdminDTO> findAllForAdmin();

    List<InventoryItem> findProductsByIds(List<UUID> productIds);

    void saveAll(List<InventoryItem> inventoryItems);

}
