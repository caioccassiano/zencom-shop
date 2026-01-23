package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
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
