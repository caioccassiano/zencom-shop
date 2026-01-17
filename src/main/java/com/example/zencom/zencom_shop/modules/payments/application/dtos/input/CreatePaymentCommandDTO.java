package com.example.zencom.zencom_shop.modules.payments.application.dtos.input;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentCommandDTO(
        UUID orderId,
        BigDecimal amount,
        String provider,
        String currency
) {
}
