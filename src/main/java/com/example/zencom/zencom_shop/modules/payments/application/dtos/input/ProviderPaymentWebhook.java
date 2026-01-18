package com.example.zencom.zencom_shop.modules.payments.application.dtos.input;

import java.time.Instant;

public record ProviderPaymentWebhook(
        String provider,
        String providerReferenceId,
        WebhookPaymentStatus status,
        Instant occurredAt,
        String failureReason
) {
}


