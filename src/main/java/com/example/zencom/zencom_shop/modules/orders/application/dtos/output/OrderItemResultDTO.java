package com.example.zencom.zencom_shop.modules.orders.application.dtos.output;

import org.springframework.scheduling.support.SimpleTriggerContext;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemResultDTO(
        String productId,
        String productName,
        BigDecimal unitPrice,
        int quantity,
        BigDecimal subtotal
) {
}


