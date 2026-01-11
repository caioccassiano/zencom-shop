package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductDeactivateIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID productId
)implements IntegrationEvent {

    @Override
    public String eventType() {
        return "ProductDeactivateIntegrationEvent";
    }

    public static ProductDeactivateIntegrationEvent now(UUID productId) {
        return new ProductDeactivateIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                productId
        );
    }
}
