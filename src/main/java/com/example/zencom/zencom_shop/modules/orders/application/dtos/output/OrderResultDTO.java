package com.example.zencom.zencom_shop.modules.orders.application.dtos.output;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.CreateOrderItemDTO;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderResultDTO(
        String orderId,
        UUID userId,
        String status,
        BigDecimal subtotal,
        BigDecimal discountTotal,
        BigDecimal total,
        List<OrderItemResultDTO> items,
        Instant createdAt,
        Instant updatedAt
) {
}


