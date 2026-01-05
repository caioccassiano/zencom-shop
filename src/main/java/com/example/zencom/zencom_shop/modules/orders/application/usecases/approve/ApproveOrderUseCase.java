package com.example.zencom.zencom_shop.modules.orders.application.usecases.approve;

import com.example.zencom.zencom_shop.modules.orders.application.dtos.input.ApproveOrderDTO;
import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;


public class ApproveOrderUseCase {

    private final OrdersRepository ordersRepository;
    private final InventoryPort inventoryPort;

    public ApproveOrderUseCase(OrdersRepository ordersRepository, InventoryPort inventoryPort) {
        this.ordersRepository = ordersRepository;
        this.inventoryPort = inventoryPort;
    }

    public void execute(ApproveOrderDTO dto) {
        OrderId orderId = OrderId.from_UUID(dto.orderId());
        Order order = ordersRepository.findById(orderId)
                        .orElseThrow(()->
                                new OrderNotFoundException("Order with id " + orderId + " not found")
                        );
        order.confirm();;
        finalizeInventory(order);
        ordersRepository.save(order);
    }

    private void finalizeInventory(Order order) {
        for(OrderItem orderItem : order.getOrderItems()) {
            inventoryPort.commit(orderItem.getProductId(), orderItem.getQuantity());
        }
    }
}
