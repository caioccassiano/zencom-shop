package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentPaidIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID aggregateId
) implements IntegrationEvent {

    @Override
    public String eventType(){
        return "PaymentPaidIntegrationEvent";
    }

    public static PaymentPaidIntegrationEvent now(UUID aggregateId) {
        return new PaymentPaidIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                aggregateId
        );
    }
}
