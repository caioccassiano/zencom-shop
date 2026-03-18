package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.inventoryItem;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultAdminDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public class InventoryItemRepositoryAdapter implements InventoryItemRepository {

    private final InventoryItemJpaRepository  jpaRepository;
    public InventoryItemRepositoryAdapter(InventoryItemJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }


    @Override
    public Optional<InventoryItem> findByProductId(ProductId productId) {
        return jpaRepository.findByProductId(productId.getId())
                .map(InventoryItemPersistenceMapper::toDomain);
    }

    @Override
    public boolean existsByProductId(ProductId productId) {
        return jpaRepository.existsByProductId(productId.getId());
    }

    @Override
    public void save(InventoryItem inventoryItem) {
        InventoryItemJpaEntity entity = InventoryItemPersistenceMapper.toEntity(inventoryItem);
        jpaRepository.save(entity);
    }

    @Override
    public List<InventoryItem> findAll() {
        return jpaRepository.findAll()
                .stream()
                .map(InventoryItemPersistenceMapper::toDomain)
                .toList();
    }


    @Override
    public List<InventoryItem> findAllWithAvailableQuantity() {
        return jpaRepository.findByAvailableQuantityGreaterThan(0)
                .stream()
                .map(InventoryItemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public List<InventoryItem> findProductsByIds(List<UUID> productIds) {
        return jpaRepository.findByProductIdIn(productIds)
                .stream()
                .map(InventoryItemPersistenceMapper::toDomain)
                .toList();
    }

    @Override
    public void saveAll(List<InventoryItem> inventoryItems) {
        var entities = inventoryItems.stream()
                .map(InventoryItemPersistenceMapper::toEntity)
                .toList();
        jpaRepository.saveAll(entities);

    }

    @Override
    public List<InventoryItemResultAdminDTO> findAllForAdmin() {
        return jpaRepository.findAllForAdmin();
    }
}
