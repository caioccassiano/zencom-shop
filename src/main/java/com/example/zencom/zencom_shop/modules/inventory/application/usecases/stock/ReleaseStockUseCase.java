package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.ReleaseStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class ReleaseStockUseCase {
    private final InventoryRepository inventoryRepository;

    public ReleaseStockUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public void execute(ReleaseStockCommandDTO command) {
        if(command.productId()==null){
            throw  new IllegalArgumentException("Product Id is required");
        }
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.releaseStock(command.quantity());
        this.inventoryRepository.save(item);

    }
}
