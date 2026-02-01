package com.example.zencom.zencom_shop.modules.payments.application.dtos.output;

import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record PaymentResultDTO(
        UUID paymentId,
        UUID orderId,
        BigDecimal amount,
        PaymentCurrency currency,
        PaymentProvider provider,
        String requestId,
        PaymentStatus status
) {
}
