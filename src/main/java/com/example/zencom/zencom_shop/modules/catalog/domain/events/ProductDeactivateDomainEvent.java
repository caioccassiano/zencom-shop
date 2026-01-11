package com.example.zencom.zencom_shop.modules.catalog.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductDeactivateDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "ProductDeactivate";
    }

    public static ProductDeactivateDomainEvent now(UUID productId) {
        return new ProductDeactivateDomainEvent(UUID.randomUUID(), Instant.now(), productId);
    }
}

