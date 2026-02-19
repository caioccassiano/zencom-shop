package com.example.zencom.zencom_shop.modules.catalog.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.GetInventoryItemByIdCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.exceptions.InventoryItemNotFoundException;
import com.example.zencom.zencom_shop.modules.catalog.application.mappers.InventoryItemResultMapper;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.GetInventoryByIdUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@Transactional(readOnly = true)
public class GetInventoryByIdUseCaseImpl implements GetInventoryByIdUseCase {
    private final InventoryItemRepository inventoryItemRepository;

    public GetInventoryByIdUseCaseImpl(InventoryItemRepository inventoryItemRepository) {
        this.inventoryItemRepository = inventoryItemRepository;
    }

    public InventoryItemResultDTO execute(GetInventoryItemByIdCommand command) {
        ProductId productId = ProductId.from_UUID(command.productId());
        InventoryItem item = this.inventoryItemRepository.findByProductId(productId)
                .orElseThrow(InventoryItemNotFoundException::new);
        return InventoryItemResultMapper.toDTO(item);
    }
}
