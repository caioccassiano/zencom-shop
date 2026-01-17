package com.example.zencom.zencom_shop.modules.payments.application.dtos.output;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResultDTO(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        String currency,
        String provider,
        String status
) {
}
