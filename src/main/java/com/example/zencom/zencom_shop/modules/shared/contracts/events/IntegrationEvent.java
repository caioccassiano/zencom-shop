package com.example.zencom.zencom_shop.modules.shared.contracts.events;

import java.time.Instant;
import java.util.UUID;

public interface IntegrationEvent<T> {
    EventMetadata metadata();

    T payload();
}
