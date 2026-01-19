package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments;

import java.util.UUID;

public record PaymentCreatedSnapshot(
        UUID paymentId,
        PaymentStatus status
) {
}
