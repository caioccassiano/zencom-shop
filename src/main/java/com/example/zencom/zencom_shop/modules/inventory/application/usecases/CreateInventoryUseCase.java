package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class CreateInventoryUseCase {

    private final InventoryRepository inventoryRepository;

    public CreateInventoryUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(CreateInventoryItemCommand command){
        if(command == null){
            throw new IllegalArgumentException("CreateInventoryItemCommand argument is null");
        }
        ProductId productId = ProductId.from_UUID(command.productId());
        if(inventoryRepository.existsByProductId(productId)){
            return;
        }
        InventoryItem inventoryItem = InventoryItem.create(productId);
        inventoryRepository.save(inventoryItem);
    }
}
