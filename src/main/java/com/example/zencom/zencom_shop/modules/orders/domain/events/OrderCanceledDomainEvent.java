package com.example.zencom.zencom_shop.modules.orders.domain.events;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.OrderCanceledIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.OrderCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record OrderCanceledDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "OrderCanceled";
    }

    public static OrderCanceledDomainEvent now(UUID orderId) {
        return new OrderCanceledDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId
        );
    }
}
