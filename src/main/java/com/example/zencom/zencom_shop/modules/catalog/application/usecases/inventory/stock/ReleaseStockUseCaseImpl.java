package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.ReleaseStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock.ReleaseStockUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional
public class ReleaseStockUseCaseImpl implements ReleaseStockUseCase {
    private final InventoryItemRepository inventoryItemRepository;

    public ReleaseStockUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
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
