package com.example.zencom.zencom_shop.modules.checkout.application.ports;

import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders.CreateOrderRequest;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.orders.OrderCreatedSnapshot;

import java.util.UUID;

public interface OrdersPort {
    OrderCreatedSnapshot createOrder(CreateOrderRequest request);
    void cancel (UUID orderId);

}
