package com.example.zencom.zencom_shop.modules.orders.application.dtos.input;

import java.math.BigDecimal;
import java.util.UUID;

public record CreateOrderItemDTO(
        UUID productId,
        String productName,
        int quantity,
        BigDecimal unitPrice
) {
}



