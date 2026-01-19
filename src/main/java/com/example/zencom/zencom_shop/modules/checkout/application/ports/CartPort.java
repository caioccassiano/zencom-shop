package com.example.zencom.zencom_shop.modules.checkout.application.ports;

import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartSnapshot;

import java.util.Optional;
import java.util.UUID;

public interface CartPort {
    CartSnapshot getActiveCart(UUID customerId);
    void checkout(UUID cartId);
}
