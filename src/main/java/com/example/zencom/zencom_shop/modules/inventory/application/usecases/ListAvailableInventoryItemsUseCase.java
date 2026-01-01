package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;

import java.util.List;

public class ListAvailableInventoryItemsUseCase {

    private final InventoryRepository inventoryRepository;

    public ListAvailableInventoryItemsUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public List<InventoryItemResultDTO> execute() {
        return inventoryRepository.findAllWithAvailableQuantity()
                .stream()
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }

    //Temporary function to test without any repository adapter
    public List<InventoryItemResultDTO> findAllAvailableItems() {
        return inventoryRepository.findAllWithAvailableQuantity()
                .stream()
                .filter(item -> item.getAvailableQuantity() > 0)
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }
}
