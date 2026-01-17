package com.example.zencom.zencom_shop.modules.payments.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentFailedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID paymentId
) implements DomainEvent {

    @Override
    public String eventType() {
        return "PaymentFailed";
    }

    @Override
    public UUID aggregateId(){
        return paymentId;
    }

    public static PaymentFailedDomainEvent now(UUID paymentId){
        return new PaymentFailedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                paymentId
        );
    }
}
