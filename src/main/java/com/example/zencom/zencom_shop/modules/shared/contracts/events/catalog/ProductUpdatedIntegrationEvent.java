package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductUpdatedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID productId
) implements IntegrationEvent {

    @Override
    public String eventType() {
        return "ProductUpdatedIntegrationEvent";
    }

    public static ProductUpdatedIntegrationEvent now(UUID productId){
        return new ProductUpdatedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                productId
        );
    }
}
