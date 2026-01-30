package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentRefundedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements IntegrationEvent {

    @Override
    public String eventType() {
        return "PaymentRefundedIntegrationEvent";
    }

    public static PaymentRefundedIntegrationEvent now(UUID aggregateId) {
        return new PaymentRefundedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId
        );
    }
}
