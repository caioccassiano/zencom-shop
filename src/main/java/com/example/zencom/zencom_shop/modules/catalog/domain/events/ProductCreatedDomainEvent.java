package com.example.zencom.zencom_shop.modules.catalog.domain.events;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.Product;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductCreatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "ProductCreated";
    }

    public static ProductCreatedDomainEvent now(UUID productId) {
        return new ProductCreatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                productId
        );
    }
}
