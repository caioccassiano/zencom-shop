package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.ListInventoryItemsUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class ListInventoryItemsUseCaseImpl implements ListInventoryItemsUseCase {
    private final InventoryItemRepository inventoryItemRepository;

    public ListInventoryItemsUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }
    public List<InventoryItemResultDTO> execute() {
        return inventoryItemRepository.findAll()
                .stream()
                .map(InventoryItemResultMapper::toDTO)
                .toList();
    }
}
