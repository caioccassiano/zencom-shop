package com.example.zencom.zencom_shop.modules.shared.contracts.events;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID orderId,
        UUID userId
) implements IntegrationEvent{

    @Override
    public String eventType() {
        return "OrderCreatedIntegrationEvent";
    }

    public static OrderCreatedIntegrationEvent now(UUID orderId, UUID userId) {
        return new OrderCreatedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId,
                userId
        );
    }
}
