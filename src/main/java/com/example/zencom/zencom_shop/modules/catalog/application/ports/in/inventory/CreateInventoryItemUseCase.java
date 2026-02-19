package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.CreateInventoryItemCommand;

public interface CreateInventoryItemUseCase {
    void execute(CreateInventoryItemCommand command);
}
