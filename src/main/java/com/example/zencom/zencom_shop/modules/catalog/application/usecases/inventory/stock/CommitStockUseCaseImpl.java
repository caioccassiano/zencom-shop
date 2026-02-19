package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.CommitStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.CommitStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class CommitStockUseCaseImpl implements CommitStockUseCase {
    InventoryItemRepository inventoryItemRepository;

    public CommitStockUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void execute(CommitStockCommandDTO command) {
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryItemRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.commit(command.quantity());
        this.inventoryItemRepository.save(item);
    }
}


