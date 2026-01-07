package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.AbandonCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import com.example.zencom.zencom_shop.modules.cart.domain.vo.CartId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AbandonCartUseCaseTest {

    private  AbandonCartUseCase abandonCartUseCase;
    private  CartRepository cartRepository;
    private final UUID userId =  UUID.randomUUID();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        abandonCartUseCase = new AbandonCartUseCase(cartRepository);
    }

    @Test
    void should_abandon_an_active_cart() {
        Cart cart = Cart.create(userId);
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        assertEquals(CartStatus.ACTIVE, cart.getStatus());

        abandonCartUseCase.execute(new AbandonCartCommandDTO(userId));
        assertEquals(CartStatus.ABANDONED, cart.getStatus());
        verify(cartRepository).findActiveByUserId(userId);
        verify(cartRepository).save(cart);
        verifyNoMoreInteractions(cartRepository);


    }

    @Test
    void should_throw_when_not_active_cart() {
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundOrINotActiveException.class, () -> abandonCartUseCase.execute(new AbandonCartCommandDTO(userId)));

    }




}