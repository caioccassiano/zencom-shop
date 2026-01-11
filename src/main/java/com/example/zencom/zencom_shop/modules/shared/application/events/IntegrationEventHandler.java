package com.example.zencom.zencom_shop.modules.shared.application.events;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

public interface IntegrationEventHandler<T  extends IntegrationEvent> {

    Class<T> eventType();

    void handle(T event);
}
