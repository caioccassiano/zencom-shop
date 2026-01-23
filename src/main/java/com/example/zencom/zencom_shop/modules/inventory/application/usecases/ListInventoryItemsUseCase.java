package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;

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
