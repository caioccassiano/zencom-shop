package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.IncreaseStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class IncreaseStockUseCaseImpl implements IncreaseStockUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public IncreaseStockUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
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
