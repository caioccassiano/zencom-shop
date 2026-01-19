package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments;

import com.example.zencom.zencom_shop.modules.checkout.application.dtos.PaymentMethod;

import java.math.BigDecimal;
import java.util.UUID;

public record CreatePaymentRequest(
        UUID orderId,
        UUID costumerId,
        BigDecimal amount,
        PaymentMethod method
) {
}
