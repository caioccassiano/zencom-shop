package com.example.zencom.zencom_shop.modules.cart.adapters.out.cart;

import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class CartRepositoryAdapter implements CartRepository {

    private final CartJpaRepository jpaRepository;

    public CartRepositoryAdapter(CartJpaRepository jpaRepository) {
        this.jpaRepository = jpaRepository;
    }

    @Override
    public Cart save(Cart cart) {
        CartJpaEntity entity = CartPersistenceMapper.toEntity(cart);
        CartJpaEntity saved = jpaRepository.save(entity);
        return CartPersistenceMapper.toDomain(saved);
    }

    @Override
    public Optional<Cart> findById(CartId cartId) {
        return jpaRepository.findById(cartId.getId())
                .map(CartPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Cart> findActiveByUserId(UUID userId) {
        return jpaRepository.findByUserIdAndStatus(userId, CartStatus.ACTIVE)
                .map(CartPersistenceMapper::toDomain);
    }
}
