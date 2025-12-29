package com.example.zencom.zencom_shop.modules.catalog.application.dtos.outputs;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public record ProductSnapShot(
        ProductId id,
        String name,
        String description,
        ProductStatus status
) {
}
