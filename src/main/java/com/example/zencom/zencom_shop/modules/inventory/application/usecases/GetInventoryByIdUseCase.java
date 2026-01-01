package com.example.zencom.zencom_shop.modules.inventory.application.usecases;

import com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.GetInventoryItemByIdCommand;
import com.example.zencom.zencom_shop.modules.inventory.application.dtos.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.inventory.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.inventory.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.inventory.application.ports.InventoryRepository;
import com.example.zencom.zencom_shop.modules.inventory.domain.entities.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public class GetInventoryByIdUseCase {
    private final InventoryRepository inventoryRepository;

    public GetInventoryByIdUseCase(InventoryRepository inventoryRepository) {
        this.inventoryRepository = inventoryRepository;
    }

    public InventoryItemResultDTO execute(GetInventoryItemByIdCommand command) {
        ProductId productId = ProductId.from(command.productId());
        InventoryItem item = this.inventoryRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        return InventoryItemResultMapper.toDTO(item);
    }
}
