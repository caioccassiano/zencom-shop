package com.example.zencom.zencom_shop.modules.orders.application.dtos.input;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public record CreateOrderCommand(
        UUID userId,
        List<CreateOrderItemDTO> items,
        BigDecimal discount
) {
}
