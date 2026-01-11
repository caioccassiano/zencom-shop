package com.example.zencom.zencom_shop.modules.shared.domain.events;

import java.time.Instant;
import java.util.UUID;

public interface DomainEvent {
    UUID eventId();
    Instant occurredAt();
    UUID aggregateId();
    String eventType();
}
