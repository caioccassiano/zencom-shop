package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inputs;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ProductStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

public record ChangeProductStatusCommand(
        ProductId id,
        ProductStatus newStatus
) {
}
