package com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.mappers;

import com.example.zencom.zencom_shop.modules.catalog.adapters.in.web.inventory.dtos.response.InventoryItemAdminResponse;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output.InventoryItemResultAdminDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.InventoryItemRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.InventoryItem;

import java.util.List;

public class InventoryHttpResponseMapper {
    private InventoryHttpResponseMapper(){}

    public static InventoryItemAdminResponse toInventoryItemAdminResponse(InventoryItemResultAdminDTO dto){
        return new InventoryItemAdminResponse(
                dto.productId(),
                dto.productName(),
                dto.availableQuantity(),
                dto.reservedQuantity(),
                dto.totalQuantity()
        );
    }
}
