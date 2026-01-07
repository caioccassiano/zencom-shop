package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.GetOrCreateCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.dtos.output.CartResultDTO;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class GetOrCreateCartUseCaseTest {
    private CartRepository cartRepository;
    private GetOrCreateCartUseCase getOrCreateCartUseCase;

    private final UUID userId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        getOrCreateCartUseCase = new GetOrCreateCartUseCase(cartRepository);
    }

    @Test
    void should_return_cart() {
        Cart cart = Cart.create(userId);
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        CartResultDTO result = getOrCreateCartUseCase.execute(
                new GetOrCreateCartCommandDTO(userId)
        );
        assertNotNull(result);
        verify(cartRepository).findActiveByUserId(userId);
        verifyNoMoreInteractions(cartRepository);
    }

    @Test
    void should_create_cart() {
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.empty());
        when(cartRepository.save(any(Cart.class))).thenAnswer(i -> i.getArgument(0));
        CartResultDTO result = getOrCreateCartUseCase.execute(
                new GetOrCreateCartCommandDTO(userId)
        );
        assertNotNull(result);

        verify(cartRepository).findActiveByUserId(userId);
        verify(cartRepository).save(any(Cart.class));
        verifyNoMoreInteractions(cartRepository);
    }

}