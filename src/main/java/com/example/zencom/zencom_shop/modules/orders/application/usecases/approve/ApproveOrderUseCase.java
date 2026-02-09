package com.example.zencom.zencom_shop.modules.orders.application.usecases.approve;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.ApproveOrderDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.mappers.OrderIntegrationEventMapper;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.orders.domain.enums.OrderStatus;
import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.Optional;
import java.util.UUID;


public class ApproveOrderUseCase {

    private final OrdersRepository ordersRepository;
    private final InventoryPort inventoryPort;
    private final IntegrationEventEmitter integrationEventEmitter;

    public ApproveOrderUseCase(OrdersRepository ordersRepository,
                               InventoryPort inventoryPort,
                               IntegrationEventEmitter integrationEventEmitter) {
        this.ordersRepository = ordersRepository;
        this.inventoryPort = inventoryPort;
        this.integrationEventEmitter = integrationEventEmitter;
    }

    public void execute(ApproveOrderDTO dto) {
        validateDto(dto);
        Order order = loadOrder(dto);
        if(order.getStatus() == OrderStatus.APPROVED) return;
        order.confirm();//core
        finalizeInventory(order);//side effects
        ordersRepository.save(order);
        UUID requestId = UUID.fromString(order.getRequestId());
        integrationEventEmitter.emitFrom(order, requestId); //side effects
    }

    private void finalizeInventory(Order order) {
        for(OrderItem orderItem : order.getOrderItems()) {
            inventoryPort.commit(orderItem.getProductId(), orderItem.getQuantity());
        }
    }

    private Order loadOrder(ApproveOrderDTO dto) {
        OrderId orderId = OrderId.from_UUID(dto.orderId());
        return this.ordersRepository.findById(orderId)
                .orElseThrow(()->
                        new OrderNotFoundException("Order with id " + orderId + " not found")
                );


    }

    private void validateDto(ApproveOrderDTO dto) {
        if(dto==null) throw new IllegalArgumentException("dto can't be null");
        if(dto.orderId()==null) throw new IllegalArgumentException("orderId can't be null");
    }
}
