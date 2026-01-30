package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class CreateInventoryUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public CreateInventoryUseCase(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void execute(CreateInventoryItemCommand command){
        if(command == null){
            throw new IllegalArgumentException("CreateInventoryItemCommand argument is null");
        }
        ProductId productId = ProductId.from_UUID(command.productId());
        if(inventoryItemRepository.existsByProductId(productId)){
            return;
        }
        InventoryItem inventoryItem = InventoryItem.create(productId);
        inventoryItemRepository.save(inventoryItem);
    }
}
