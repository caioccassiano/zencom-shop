package com.example.zencom.zencom_shop.modules.cart.application.ports.cart;

import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;

import java.util.Optional;
import java.util.UUID;

public interface CartRepository {
    Cart save(Cart cart);

    Optional<Cart> findById(CartId cartId);

    Optional<Cart> findActiveByUserId(UUID userId);
}
