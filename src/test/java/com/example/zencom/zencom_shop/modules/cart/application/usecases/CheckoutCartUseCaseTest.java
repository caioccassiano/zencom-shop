package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.CheckoutCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.CartItemNotFoundException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CheckoutCartUseCaseTest {

    private CartRepository cartRepository;
    private CheckoutCartUseCase checkoutCartUseCase;
    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        checkoutCartUseCase = new CheckoutCartUseCase(cartRepository);
    }

    @Test
    void should_checkout_cart() {
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                3,
                new BigDecimal("100.00")
        );
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        assertEquals(CartStatus.ACTIVE, cart.getStatus());

        checkoutCartUseCase.execute(new CheckoutCartCommandDTO(userId));
        assertEquals(CartStatus.CHECKED_OUT, cart.getStatus());
        verify(cartRepository).findActiveByUserId(userId);
        verify(cartRepository).save(cart);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void should_throw_exception_when_cart_not_found() {
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundOrINotActiveException.class, () -> checkoutCartUseCase.execute(new CheckoutCartCommandDTO(userId)));
        verify(cartRepository).findActiveByUserId(userId);
        verifyNoMoreInteractions(cartRepository);
    }

}