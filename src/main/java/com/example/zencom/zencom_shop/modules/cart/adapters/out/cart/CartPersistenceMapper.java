package com.example.zencom.zencom_shop.modules.cart.adapters.out.cart;

import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.CartItem;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.List;

public class CartPersistenceMapper {
    private CartPersistenceMapper() {}

    public static CartJpaEntity toEntity(Cart cart) {
        CartJpaEntity entity = new CartJpaEntity();
        entity.setId(cart.getCartId().getId());
        entity.setUserId(cart.getUserId());
        entity.setStatus(cart.getStatus());
        entity.setCreatedAt(cart.getCreatedAt());
        entity.setUpdatedAt(cart.getUpdatedAt());
        entity.setItems(
                cart.getCartItemList().stream()
                        .map(item -> new CartItemJpaEmbeddable(
                                item.getProductId().getId(),
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getAddedAt()
                        ))
                        .toList()
        );
        return entity;
    }

    public static Cart toDomain(CartJpaEntity entity) {
        List<CartItem> items = entity.getItems().stream()
                .map(i -> new CartItem(
                        ProductId.from_UUID(i.getProductId()),
                        i.getQuantity(),
                        i.getUnitPrice(),
                        i.getAddedAt()
                ))
                .toList();
        return Cart.restore(
                CartId.from_UUID(entity.getId()),
                entity.getUserId(),
                entity.getStatus(),
                items,
                entity.getCreatedAt(),
                entity.getUpdatedAt()
        );
    }
}
