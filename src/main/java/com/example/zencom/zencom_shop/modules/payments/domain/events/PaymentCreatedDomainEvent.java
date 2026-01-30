package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCreatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId,
        UUID orderId
) implements DomainEvent {

    public static final String TYPE = "payments.payment_created";

    @Override
    public String eventType() {
        return TYPE;
    }

    @Override public UUID aggregateId() {
        return paymentId;
    }

    public static PaymentCreatedDomainEvent now(UUID paymentId, UUID orderId) {
        return new PaymentCreatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId,
                orderId
        );
    }
}
