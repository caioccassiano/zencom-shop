package com.example.zencom.zencom_shop.modules.orders.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record OrderApprovedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
)
implements DomainEvent {

    @Override
    public String eventType() {
        return "OrderApprovedDomain";
    }

    public static OrderApprovedDomainEvent now(UUID orderId) {
        return new OrderApprovedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId
        );
    }

}

