package com.example.zencom.zencom_shop.modules.orders.application.mappers;

import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderApprovedDomainEvent;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderCanceledDomainEvent;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCanceledIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderPaidIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Optional;
import java.util.UUID;

public class OrderIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    @Override
    public Optional<IntegrationEvent<?>> toIntegration(AggregateRoot aggregate, DomainEvent domainEvent, UUID correlationId) {
        if(!(aggregate instanceof Order order)) return Optional.empty();
        if (domainEvent instanceof OrderCreatedDomainEvent event) {
            var items = order.getOrderItems().stream()
                    .map(item -> new OrderCreatedIntegrationEvent.Item(item.getProductId().getId(),item.getQuantity()))
                    .toList();

            var payload = new OrderCreatedIntegrationEvent.Payload(
                    order.getOrderId().getId(),
                    order.getUserId(),
                    order.getTotal(),
                    items
            );

            return Optional.of(new OrderCreatedIntegrationEvent(
                    new EventMetadata(event.eventId(), event.occurredAt(), OrderCreatedIntegrationEvent.TYPE, correlationId),
                    payload
            ));
        }
        if (domainEvent instanceof OrderApprovedDomainEvent event) {
            var items = order.getOrderItems().stream()
                    .map(item -> new OrderPaidIntegrationEvent.Item(item.getProductId().getId(),item.getQuantity()))
                    .toList();
            var payload = new OrderPaidIntegrationEvent.OrderPaidPayload(
                    order.getOrderId().getId(),
                    order.getUserId(),
                    order.getReservationId(),
                    order.getTotal(),
                    items
            );
            return Optional.of(new OrderPaidIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            OrderPaidIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));
        }
        if (domainEvent instanceof OrderCanceledDomainEvent event) {
            var items = order.getOrderItems().stream()
                    .map(item -> new OrderCanceledIntegrationEvent.Item(item.getProductId().getId(),item.getQuantity()))
                    .toList();
            var payload = new OrderCanceledIntegrationEvent.OrderCanceledPayload(
                    order.getOrderId().getId(),
                    order.getUserId(),
                    order.getTotal(),
                    items,
                    order.getReservationId()
            );
            return Optional.of(new OrderCanceledIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            OrderCanceledIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));
        }
        return Optional.empty();
    }
}
