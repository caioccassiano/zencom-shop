package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentAuthorizedIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements IntegrationEvent {
    @Override
    public String eventType() {
        return "PaymentAuthorizedIntegrationEvent";
    }
    public static PaymentAuthorizedIntegrationEvent now(UUID aggregateId){
        return new PaymentAuthorizedIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId
        );
    }
}
