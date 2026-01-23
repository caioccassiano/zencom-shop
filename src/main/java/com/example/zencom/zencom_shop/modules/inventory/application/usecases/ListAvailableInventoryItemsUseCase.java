package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;

import java.util.List;

public class ListAvailableInventoryItemsUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public ListAvailableInventoryItemsUseCase(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public List<InventoryItemResultDTO> execute() {
        return inventoryItemRepository.findAllWithAvailableQuantity()
                .stream()
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }

    //Temporary function to test without any repository adapter
    public List<InventoryItemResultDTO> findAllAvailableItems() {
        return inventoryItemRepository.findAllWithAvailableQuantity()
                .stream()
                .filter(item -> item.getAvailableQuantity() > 0)
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }
}
