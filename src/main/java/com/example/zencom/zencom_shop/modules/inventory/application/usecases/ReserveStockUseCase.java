package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.ReserveStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;

import java.util.Optional;

public class ReserveStockUseCase {

    private final InventoryRepository inventoryRepository;

    public ReserveStockUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(ReserveStockCommandDTO command){
        InventoryItem item = this.inventoryRepository.findByProductId(command.productId())
                .orElseThrow(InventoryItemNotFoundException::new);
        item.reserveStock(command.quantity());
        this.inventoryRepository.save(item);

    }
}
