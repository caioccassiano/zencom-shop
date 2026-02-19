package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.ReserveStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.ReserveStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReserveStockUseCaseImpl implements ReserveStockUseCase {

    private final InventoryItemRepository inventoryItemRepository;

    public ReserveStockUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public void execute(ReserveStockCommandDTO command){
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryItemRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        item.reserveStock(command.quantity());
        this.inventoryItemRepository.save(item);

    }
}
