package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class IncreaseStockUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public IncreaseStockUseCase(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void execute(AddStockCommandDTO command) {
        if(command.productId() == null) {
            return;
        }
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryItemRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.addStock(command.quantity());
        this.inventoryItemRepository.save(item);
    }

}
