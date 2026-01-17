package com.example.zencom.zencom_shop.modules.shared.application.utils;

import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.AggrgateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;

public class IntegrationEventEmitter {

    public interface DomainToIntegrationMapper{
        Optional<IntegrationEvent> toIntegration(DomainEvent domainEvent);
    }

    private final IntegrationEventPublisher publisher;
    private final DomainToIntegrationMapper mapper;

    public IntegrationEventEmitter(IntegrationEventPublisher publisher, DomainToIntegrationMapper mapper) {
        this.publisher = publisher;
        this.mapper = mapper;
    }

    public void emitFrom(AggrgateRoot aggregate) {
        List<IntegrationEvent> integrationsEvents = aggregate.pullDomainEvents()
                .stream()
                .map(mapper::toIntegration)
                .flatMap(Optional::stream)
                .toList();
        if(integrationsEvents.isEmpty()) return;
        publisher.publish(integrationsEvents);
    }
}
