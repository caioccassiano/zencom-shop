package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.ReserveStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class ReserveStockUseCase {

    private final InventoryRepository inventoryRepository;

    public ReserveStockUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(ReserveStockCommandDTO command){
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.reserveStock(command.quantity());
        this.inventoryRepository.save(item);

    }
}
