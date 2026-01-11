package com.example.zencom.zencom_shop.modules.catalog.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductUpdatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "ProductUpdated";
    }

    public static ProductUpdatedDomainEvent now(UUID productId){
        return new ProductUpdatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                productId
        );
    }
}
