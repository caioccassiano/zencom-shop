package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.inventoryItem;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultAdminDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import jakarta.persistence.NamedNativeQuery;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface InventoryItemJpaRepository extends JpaRepository<InventoryItemJpaEntity, UUID> {
    Optional<InventoryItemJpaEntity> findByProductId(UUID productId);
    boolean existsByProductId(UUID productId);
    List<InventoryItemJpaEntity> findByAvailableQuantityGreaterThan(Integer quantity);
    List<InventoryItemJpaEntity> findByProductIdIn(List<UUID> productIds);
    @Query("""
    SELECT new com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultAdminDTO(
        p.id,
        p.name,
        i.availableQuantity,
        i.reservedQuantity,
        (i.availableQuantity + i.reservedQuantity)
    )
    FROM InventoryItemJpaEntity i
    JOIN ProductJpaEntity p ON p.id = i.productId
""")
    List<InventoryItemResultAdminDTO> findAllForAdmin();
}
