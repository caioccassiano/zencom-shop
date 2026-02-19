package com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.stock;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.CommitStockCommandDTO;

public interface CommitStockUseCase {
    void execute(CommitStockCommandDTO command);
}
