package com.example.zencom.zencom_shop.modules.orders.application.usecases.cancel;

import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.Optional;
import java.util.UUID;

public class CancelOrderUseCase {
    private final OrdersRepository ordersRepository;
    private final IntegrationEventEmitter  integrationEventEmitter;

    public CancelOrderUseCase(
            OrdersRepository ordersRepository,
            IntegrationEventEmitter integrationEventEmitter
            ) {
        this.ordersRepository = ordersRepository;
        this.integrationEventEmitter = integrationEventEmitter;
    }

    public void execute(UUID orderId) {
        Order order = this.ordersRepository.findById(OrderId.from_UUID(orderId))
                .orElseThrow(()->
                        new OrderNotFoundException("Order with id " + orderId + " not found"));
        order.cancel();
        this.ordersRepository.save(order);
        integrationEventEmitter.emitFrom(order);
    }
}
