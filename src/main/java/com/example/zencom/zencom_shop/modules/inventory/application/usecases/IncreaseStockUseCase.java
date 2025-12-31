package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class IncreaseStockUseCase {

    private final InventoryRepository inventoryRepository;

    public IncreaseStockUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(AddStockCommandDTO command) {
        if(command.productId() == null) {
            return;
        }
        InventoryItem item = this.inventoryRepository.findByProductId(command.productId())
                .orElseThrow(InventoryItemNotFoundException::new);
        item.addStock(command.quantity());
        this.inventoryRepository.save(item);
    }

}
