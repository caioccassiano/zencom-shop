package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.InventoryItemRepository;

import java.util.List;

public class ListInventoryItemsUseCase {
    private final InventoryItemRepository inventoryItemRepository;

    public ListInventoryItemsUseCase(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }
    public List<InventoryItemResultDTO> execute() {
        return inventoryItemRepository.findAll()
                .stream()
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }
}
