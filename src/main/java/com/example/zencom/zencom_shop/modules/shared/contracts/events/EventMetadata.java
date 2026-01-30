package com.example.zencom.zencom_shop.modules.shared.contracts.events;

import java.time.Instant;
import java.util.UUID;

public record EventMetadata(
        UUID eventId,
        Instant occurredAt,
        String eventType,
        UUID correlationId
) {
    public static EventMetadata now(String eventType, UUID correlationId) {
        return new EventMetadata(
                UUID.randomUUID(),
                Instant.now(),
                eventType,
                correlationId
        );
    }
}
