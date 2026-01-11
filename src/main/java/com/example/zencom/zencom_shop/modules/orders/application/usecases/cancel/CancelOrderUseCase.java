package com.example.zencom.zencom_shop.modules.orders.application.usecases.cancel;

import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.Optional;
import java.util.UUID;

public class CancelOrderUseCase {
    private final OrdersRepository ordersRepository;
    private final OrderIntegrationEventMapper eventMapper;
    private final IntegrationEventPublisher eventPublisher;

    public CancelOrderUseCase(
            OrdersRepository ordersRepository,
            OrderIntegrationEventMapper eventMapper,
            IntegrationEventPublisher eventPublisher
            ) {
        this.ordersRepository = ordersRepository;
        this.eventMapper = eventMapper;
        this.eventPublisher = eventPublisher;
    }

    public void execute(UUID orderId) {
        Order order = this.ordersRepository.findById(OrderId.from_UUID(orderId))
                .orElseThrow(()->
                        new OrderNotFoundException("Order with id " + orderId + " not found"));
        order.cancel();
        this.ordersRepository.save(order);
        publishEvent(order);
    }

    private void publishEvent(Order order) {
        var integrations = order.pullDomainEvents()
                .stream()
                .map(eventMapper::toIntegration)
                .flatMap(Optional::stream)
                .toList();
        eventPublisher.publish(integrations);
    }
}
