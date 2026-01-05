package com.example.zencom.zencom_shop.modules.orders.application.usecases.inventory;

import com.example.zencom.zencom_shop.modules.orders.application.ports.inventory.InventoryPort;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
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

    }

    private void
}
