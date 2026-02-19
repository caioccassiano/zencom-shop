package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultDTO;

import java.util.List;

public interface ListInventoryItemsUseCase {
    List<InventoryItemResultDTO> execute();
}
