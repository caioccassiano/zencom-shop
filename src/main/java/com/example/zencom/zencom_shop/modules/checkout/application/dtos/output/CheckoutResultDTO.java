package com.example.zencom.zencom_shop.modules.checkout.application.dtos.output;

import com.example.zencom.zencom_shop.modules.checkout.application.dtos.PaymentMethod;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.PaymentStatus;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutResultDTO(
        UUID orderId,
        UUID paymentId,
        UUID reservationId,
        BigDecimal totalAmount,
        PaymentStatus status
) {
}
