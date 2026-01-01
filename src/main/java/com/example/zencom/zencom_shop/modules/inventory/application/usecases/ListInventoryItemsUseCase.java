package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;

import java.util.List;

public class ListInventoryItemsUseCase {
    private final InventoryRepository inventoryRepository;

    public ListInventoryItemsUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }
    public List<InventoryItemResultDTO> execute() {
        return inventoryRepository.findAll()
                .stream()
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }
}
