package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.inventoryItem;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemJpaRepository extends JpaRepository<InventoryItemJpaEntity, UUID> {
    Optional<InventoryItemJpaEntity> findByProductId(UUID productId);
    boolean existsByProductId(UUID productId);
    List<InventoryItemJpaEntity> findByAvailableQuantityGreaterThan(Integer quantity);
    List<InventoryItemJpaEntity> findByProductIdIn(List<UUID> productIds);
}
