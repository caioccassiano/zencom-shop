package com.example.zencom.zencom_shop.modules.shared.application.utils;

import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public class IntegrationEventEmitter {

    public interface DomainToIntegrationMapper{
        Optional<IntegrationEvent<?>> toIntegration(AggregateRoot aggrgate, DomainEvent domainEvent, UUID correlationId);
    }

    private final IntegrationEventPublisher publisher;
    private final DomainToIntegrationMapper mapper;

    public IntegrationEventEmitter(IntegrationEventPublisher publisher, DomainToIntegrationMapper mapper) {
        this.publisher = publisher;
        this.mapper = mapper;
    }

    public void emitFrom(AggregateRoot aggregate, UUID correlationId) {
        List<IntegrationEvent<?>> integrationsEvents = aggregate.pullDomainEvents()
                .stream()
                .map(event -> mapper.toIntegration(aggregate, event, correlationId))
                .flatMap(Optional::stream)
                .toList();
        if(integrationsEvents.isEmpty()) return;
        publisher.publish(integrationsEvents);
    }
}
