package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.UpdateCartItemQuantityCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UpdateCartItemQuantityUseCaseTest {
    private UpdateCartItemQuantityUseCase updateCartItemQuantityUseCase;
    private CartRepository cartRepository;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        updateCartItemQuantityUseCase = new UpdateCartItemQuantityUseCase(cartRepository);
    }

    @Test
    void should_update_cart_item_quantity() {
        Cart cart = Cart.create(userId);
        ProductId productId = ProductId.newId();
        cart.addNewItem(
                productId,
                3,
                new BigDecimal("15.00")
        );
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));

        assertEquals(3, cart.getCartItemList().getFirst().getQuantity());

        updateCartItemQuantityUseCase.execute(
                new UpdateCartItemQuantityCommandDTO(
                        productId.getId(),
                        userId,
                        7
                ));
        assertEquals(7, cart.getCartItemList().getFirst().getQuantity());




    }

    @Test
    void should_remove_item_when_quantity_zero() {
        Cart cart = Cart.create(userId);
        ProductId productId = ProductId.newId();
        cart.addNewItem(
                productId,
                3,
                new BigDecimal("15.00")
        );
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        assertEquals(1, cart.getCartItemList().size());
        assertEquals(3, cart.getCartItemList().getFirst().getQuantity());
        updateCartItemQuantityUseCase.execute(
                new UpdateCartItemQuantityCommandDTO(
                        productId.getId(),
                        userId,
                        0)
        );
        assertEquals(0, cart.getCartItemList().size());
        verify(cartRepository).findActiveByUserId(userId);
        verify(cartRepository).save(cart);
    }

}