package com.example.zencom.zencom_shop.support;

import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.util.List;

public class NoOpIntegrationEventPublisher implements IntegrationEventPublisher {


    @Override
    public void publish(List<IntegrationEvent> events) {

    }

    @Override
    public void publish(IntegrationEvent event) {
        IntegrationEventPublisher.super.publish(event);
    }
}
