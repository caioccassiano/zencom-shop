package com.example.zencom.zencom_shop.modules.orders.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record OrderCreatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId,
        UUID userId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "OrderCreatedDomain";
    }

    public static OrderCreatedDomainEvent now( UUID orderId, UUID userId) {
        return new OrderCreatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId,
                userId
        );
    }
}
