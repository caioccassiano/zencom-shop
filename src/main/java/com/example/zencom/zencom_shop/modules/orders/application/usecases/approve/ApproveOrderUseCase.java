package com.example.zencom.zencom_shop.modules.orders.application.usecases.approve;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.ApproveOrderDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.Optional;


public class ApproveOrderUseCase {

    private final OrdersRepository ordersRepository;
    private final InventoryPort inventoryPort;
    private final IntegrationEventPublisher eventPublisher;
    private final OrderIntegrationEventMapper eventMapper;

    public ApproveOrderUseCase(OrdersRepository ordersRepository,
                               InventoryPort inventoryPort,
                               IntegrationEventPublisher eventPublisher,
                               OrderIntegrationEventMapper eventMapper) {
        this.ordersRepository = ordersRepository;
        this.inventoryPort = inventoryPort;
        this.eventPublisher = eventPublisher;
        this.eventMapper = eventMapper;
    }

    public void execute(ApproveOrderDTO dto) {
        OrderId orderId = OrderId.from_UUID(dto.orderId());
        Order order = ordersRepository.findById(orderId)
                        .orElseThrow(()->
                                new OrderNotFoundException("Order with id " + orderId + " not found")
                        );
        order.confirm();;
        finalizeInventory(order); //core
        ordersRepository.save(order);
        publishEvents(order); //side effects
    }

    private void finalizeInventory(Order order) {
        for(OrderItem orderItem : order.getOrderItems()) {
            inventoryPort.commit(orderItem.getProductId(), orderItem.getQuantity());
        }
    }

    private void publishEvents(Order order) {
        var integrationEvents = order.pullDomainEvents()
                .stream()
                .map(eventMapper::toIntegration)
                .flatMap(Optional::stream)
                .toList();
        eventPublisher.publish(integrationEvents);

    }
}
