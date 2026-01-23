package com.example.zencom.zencom_shop.modules.inventory.application.usecases.stock;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.ReleaseStockCommandDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class ReleaseStockUseCase {
    private final InventoryItemRepository inventoryItemRepository;

    public ReleaseStockUseCase(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void execute(ReleaseStockCommandDTO command) {
        if(command.productId()==null){
            throw  new IllegalArgumentException("Product Id is required");
        }
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryItemRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.releaseStock(command.quantity());
        this.inventoryItemRepository.save(item);

    }
}
