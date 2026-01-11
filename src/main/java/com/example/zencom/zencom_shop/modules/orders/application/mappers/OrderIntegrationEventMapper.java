package com.example.zencom.zencom_shop.modules.orders.application.mappers;

import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderApprovedDomainEvent;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.OrderCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.OrderPaidIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Objects;
import java.util.Optional;

public class OrderIntegrationEventMapper {

    public Optional<IntegrationEvent> toIntegration(DomainEvent domainEvent) {
        if (domainEvent instanceof OrderCreatedDomainEvent event) {
            return Optional.of(new OrderCreatedIntegrationEvent(
                    event.eventId(),
                    event.occurredAt(),
                    event.aggregateId(),
                    event.userId()
            ));
        }
        if (domainEvent instanceof OrderApprovedDomainEvent event) {
            return Optional.of(new OrderPaidIntegrationEvent(
                    event.eventId(),
                    event.occurredAt(),
                    event.aggregateId()
            ));
        }
        return Optional.empty();
    }
}
