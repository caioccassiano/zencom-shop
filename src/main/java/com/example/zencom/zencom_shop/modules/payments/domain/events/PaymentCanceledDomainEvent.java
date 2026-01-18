package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCanceledDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId,
        String reason
) implements DomainEvent {

    @Override
    public String eventType() {
        return "PaymentCanceled";
    }
    @Override
    public UUID aggregateId() {
        return paymentId;
    }

    public static PaymentCanceledDomainEvent now(UUID paymentId, String reason){
        return new PaymentCanceledDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId,
                reason
        );
    }
}
