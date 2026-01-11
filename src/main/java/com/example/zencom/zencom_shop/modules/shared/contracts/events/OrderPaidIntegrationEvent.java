package com.example.zencom.zencom_shop.modules.shared.contracts.events;

import java.time.Instant;
import java.util.UUID;

//OrderApprovedEvent in the domain layer
public record OrderPaidIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID orderId
) implements IntegrationEvent{

    @Override
    public String eventType() {
        return "OrderApprovedIntegrationEvent";
    }

    public static OrderPaidIntegrationEvent now(UUID orderId) {
        return new OrderPaidIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId);
    }
}
