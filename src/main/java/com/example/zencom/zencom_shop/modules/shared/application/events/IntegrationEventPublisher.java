package com.example.zencom.zencom_shop.modules.shared.application.events;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.util.List;

public interface IntegrationEventPublisher {

    void publish(List<IntegrationEvent> events);

    default void publish(IntegrationEvent event) {
        publish(List.of(event));
    }
}


