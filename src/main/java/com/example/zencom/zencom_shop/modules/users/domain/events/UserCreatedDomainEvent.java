package com.example.zencom.zencom_shop.modules.users.domain.events;

import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedDomainEvent(
        UUID eventId,
        Instant occurredAt,
        UUID userId
) implements DomainEvent {

    public static final String TYPE = "users.user_created";

    @Override
    public String eventType() {
        return TYPE;
    }

    @Override
    public UUID aggregateId(){
        return userId();
    }

    public static UserCreatedDomainEvent now(UUID userId) {
        return new UserCreatedDomainEvent(
                UUID.randomUUID(),
                Instant.now(),
                userId
        );
    }

}
