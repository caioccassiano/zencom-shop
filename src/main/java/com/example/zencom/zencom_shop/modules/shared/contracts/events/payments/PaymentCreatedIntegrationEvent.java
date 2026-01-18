package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCreatedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        UUID orderId,
        String provider
) implements IntegrationEvent {
    @Override
    public String eventType() {
        return "PaymentCreatedIntegrationEvent";
    }

    public static PaymentCreatedIntegrationEvent create(UUID aggregateId, UUID orderId, String provider) {
        return new PaymentCreatedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId,
                orderId,
                provider
                );
    }


}
