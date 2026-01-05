package com.example.zencom.zencom_shop.modules.orders.application.usecases.cancel;

import com.example.zencom.zencom_shop.modules.orders.application.exception.OrderNotFoundException;
import com.example.zencom.zencom_shop.modules.orders.application.ports.orders.OrdersRepository;
import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.UUID;

public class CancelOrderUseCase {
    private final OrdersRepository ordersRepository;
    public CancelOrderUseCase(OrdersRepository ordersRepository) {
        this.ordersRepository = ordersRepository;
    }

    public void execute(UUID orderId) {
        Order order = this.ordersRepository.findById(OrderId.from_UUID(orderId))
                .orElseThrow(()->
                        new OrderNotFoundException("Order with id " + orderId + " not found"));
        order.cancel();
        this.ordersRepository.save(order);
    }
}
