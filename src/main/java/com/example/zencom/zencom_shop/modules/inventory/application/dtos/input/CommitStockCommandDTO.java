package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.UUID;

public record CommitStockCommandDTO (
    UUID productId,
    int quantity
){}
