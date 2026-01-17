package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCreatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "PaymentCreated";
    }

    @Override public UUID aggregateId() {
        return paymentId;
    }

    public static PaymentCreatedDomainEvent now(UUID paymentId) {
        return new PaymentCreatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId);
    }
}
