package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.CommitStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class CommitStockUseCase {
    InventoryRepository inventoryRepository;

    public CommitStockUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(CommitStockCommandDTO command) {
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.commit(command.quantity());
        this.inventoryRepository.save(item);
    }
}


