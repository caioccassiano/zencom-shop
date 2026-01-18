package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentFailedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        String reason
) implements IntegrationEvent {

    @Override
    public String eventType() {
        return "PaymentFailedIntegrationEvent";
    }

    public static PaymentFailedIntegrationEvent now(UUID aggregateId, String reason) {
        return new PaymentFailedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId,
                reason
        );
    }
}
