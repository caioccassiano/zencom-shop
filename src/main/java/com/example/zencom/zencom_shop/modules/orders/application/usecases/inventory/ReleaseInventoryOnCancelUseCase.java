package com.example.zencom.zencom_shop.modules.orders.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.OrderItem;
import com.example.zencom.zencom_shop.modules.orders.domain.enums.OrderStatus;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.UUID;

public class ReleaseInventoryOnCancelUseCase {
    private final OrdersRepository ordersRepository;
    private final InventoryPort inventoryPort;

    public ReleaseInventoryOnCancelUseCase(OrdersRepository ordersRepository, InventoryPort inventoryPort) {
        this.ordersRepository = ordersRepository;
        this.inventoryPort = inventoryPort;
    }

    public void execute(UUID orderId) {
        OrderId id = OrderId.from_UUID(orderId);
        Order order = this.ordersRepository.findById(id)
                .orElseThrow(()-> new OrderNotFoundException("Order not found"));
        order.cancel();
        releaseInventory(order);
    }

    private void releaseInventory(Order order) {
        for(OrderItem orderItem : order.getOrderItems()) {
            inventoryPort.release(orderItem.getProductId(), orderItem.getQuantity());
        }

    }

}
