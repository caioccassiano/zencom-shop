package com.example.zencom.zencom_shop.modules.orders.application.ports.orders;

import com.example.zencom.zencom_shop.modules.orders.domain.entities.Order;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface OrdersRepository {
    Order save(Order order);

    Optional<Order> findById(OrderId orderId);

    List<Order> findAll();

    List<Order> findByUserId(UUID userId);


}
