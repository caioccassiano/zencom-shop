package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentAuthorizedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "PaymentAuthorized";
    }

    @Override
    public UUID aggregateId() {
        return paymentId;
    }

    public static PaymentAuthorizedDomainEvent now(UUID paymentId) {
        return new PaymentAuthorizedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId
        );
    }
}
