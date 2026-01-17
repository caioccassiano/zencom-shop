package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentRefundedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "PaymentRefunded";
    }

    @Override
    public UUID aggregateId() {
        return paymentId;
    }

    public static PaymentRefundedDomainEvent now(UUID paymentId) {
        return new PaymentRefundedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId
        );
    }
}
