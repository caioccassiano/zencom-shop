package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductCreatedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID productId
) implements IntegrationEvent {

    @Override
    public String eventType() {
        return "ProductCreatedIntegrationEvent";
    }

    public static ProductCreatedIntegrationEvent now(UUID productId) {
        return new ProductCreatedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                productId
        );
    }
}
