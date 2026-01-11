package com.example.zencom.zencom_shop.modules.shared.contracts.events;

import java.time.Instant;
import java.util.UUID;

public interface IntegrationEvent {
    UUID eventId();
    Instant occurredAt();
    String eventType();
    default UUID correlationId(){return null;};
}
