package com.example.zencom.zencom_shop.modules.cart.adapters.out.cart;

import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CartJpaRepository extends JpaRepository<CartJpaEntity, UUID> {
    Optional<CartJpaEntity> findByUserIdAndStatus(UUID userId, CartStatus status);
}
