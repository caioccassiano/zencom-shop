package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.CreateInventoryItemCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CreateInventoryItemUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CreateInventoryItemUseCaseImpl implements CreateInventoryItemUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public CreateInventoryItemUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
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
