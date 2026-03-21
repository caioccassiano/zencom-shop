package com.example.zencom.zencom_shop.modules.cart.adapters.out.checkout;

import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import com.example.zencom.zencom_shop.modules.checkout.application.ports.CartPort;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartItemSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.cart.CartSnapshot;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public class CheckoutCartPortAdapter implements CartPort {

    private final CartRepository cartRepository;

    public CheckoutCartPortAdapter(CartRepository cartRepository) {
        this.cartRepository = cartRepository;
    }

    @Override
    public CartSnapshot getActiveCart(UUID customerId) {
        Cart cart = cartRepository.findActiveByUserId(customerId)
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found for customer"));
        return new CartSnapshot(
                cart.getCartId().getId(),
                cart.getUserId(),
                cart.getCartItemList().stream()
                        .map(item -> new CartItemSnapshot(
                                item.getProductId().getId(),
                                item.getQuantity(),
                                item.getUnitPrice(),
                                item.getAddedAt()
                        ))
                        .toList(),
                cart.subtotal(),
                cart.getCreatedAt(),
                cart.getUpdatedAt()
        );
    }

    @Override
    public void checkout(UUID cartId) {
        Cart cart = cartRepository.findById(CartId.from_UUID(cartId))
                .orElseThrow(() -> new CartNotFoundOrINotActiveException("Cart not found"));
        cart.checkout();
        cartRepository.save(cart);
    }
}
