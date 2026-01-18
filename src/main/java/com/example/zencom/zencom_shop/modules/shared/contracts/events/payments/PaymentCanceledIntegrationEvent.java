package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCanceledIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        String reason
) implements IntegrationEvent {

    @Override
    public String eventType() {
        return "PaymentCanceledIntegrationEvent";
    }

    public static PaymentCanceledIntegrationEvent now(UUID aggregateId, String reason) {
        return new PaymentCanceledIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId,
                reason
        );
    }
}
