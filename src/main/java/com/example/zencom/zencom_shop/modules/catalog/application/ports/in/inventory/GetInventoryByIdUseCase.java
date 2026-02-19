package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.GetInventoryItemByIdCommand;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;

public interface GetInventoryByIdUseCase {
    InventoryItemResultDTO execute(GetInventoryItemByIdCommand command);
}
