package com.example.zencom.zencom_shop.modules.payments.application.dtos.input;

import java.time.Instant;
import java.util.UUID;

public record CancelPaymentCommandDTO(
        String providerReferenceId,
        String reason,
        Instant canceledAt
) {
}
