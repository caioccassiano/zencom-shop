package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.ClearCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
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

class ClearCartUseCaseTest {

    private ClearCartUseCase clearCartUseCase;
    private CartRepository cartRepository;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        clearCartUseCase = new ClearCartUseCase(cartRepository);
    }

    @Test
    void should_clear_cart() {
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                3,
                new BigDecimal("12.00")
        );
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        assertEquals(1, cart.getCartItemList().size());

        clearCartUseCase.execute(
                new ClearCartCommandDTO(userId)
        );

        assertEquals(0, cart.getCartItemList().size());
        verify(cartRepository, times(1)).findActiveByUserId(userId);
        verify(cartRepository).save(cart);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void should_throw_exception_when_cart_not_found() {
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundOrINotActiveException.class, () -> clearCartUseCase.execute(
                new ClearCartCommandDTO(userId)
        ));
        verify(cartRepository, times(1)).findActiveByUserId(userId);
    }

}