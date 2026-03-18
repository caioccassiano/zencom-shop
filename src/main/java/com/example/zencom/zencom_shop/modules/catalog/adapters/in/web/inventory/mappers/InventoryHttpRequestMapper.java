package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.mappers;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request.IncreaseStockCommandRequest;
import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.request.ReleaseStockCommandRequest;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.AddStockCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.ReleaseStockCommandDTO;

import java.util.UUID;

public class InventoryHttpRequestMapper {
    private InventoryHttpRequestMapper() {}

    public static AddStockCommandDTO toAddStockCommandDTO(IncreaseStockCommandRequest request, UUID productId) {
        return new AddStockCommandDTO(
                productId,
                request.quantity()
        );
    }

    public static ReleaseStockCommandDTO toReleaseStockCommandDTO(ReleaseStockCommandRequest request, UUID productId) {
        return new ReleaseStockCommandDTO(
                productId,
                request.quantity()
        );
    }
}
