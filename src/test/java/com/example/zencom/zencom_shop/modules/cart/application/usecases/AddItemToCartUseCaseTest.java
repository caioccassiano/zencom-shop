package com.example.zencom.zencom_shop.modules.cart.application.usecases;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.AddItemToCartCommandDTO;
import com.example.zencom.zencom_shop.modules.cart.application.exception.CartNotFoundOrINotActiveException;
import com.example.zencom.zencom_shop.modules.cart.application.exception.ProductNotFoundException;
import com.example.zencom.zencom_shop.modules.cart.application.ports.cart.CartRepository;
import com.example.zencom.zencom_shop.modules.cart.application.ports.catalog.CatalogPort;
import com.example.zencom.zencom_shop.modules.cart.domain.entities.Cart;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AddItemToCartUseCaseTest {

    private CartRepository cartRepository;
    private AddItemToCartUseCase addItemToCartUseCase;
    private CatalogPort catalogPort;
    private final UUID userId = UUID.randomUUID();
    private final ProductId productId = ProductId.newId();

    @BeforeEach
    void setUp() {
        cartRepository = mock(CartRepository.class);
        catalogPort = mock(CatalogPort.class);
        addItemToCartUseCase = new AddItemToCartUseCase(cartRepository, catalogPort);
    }

    @Test
    void should_add_item_to_cart() {
        Cart cart = Cart.create(userId);
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        when(catalogPort.findActiveProduct(productId)).thenReturn(Optional.of(new CatalogPort.ProductSnapshot(
                productId,
                new BigDecimal("100.00")
        )));
        addItemToCartUseCase.execute(new AddItemToCartCommandDTO(
                userId,
                productId.getId(),
                3
        ));
        assertEquals(1, cart.getCartItemList().size());
        assertEquals(new BigDecimal("300.00"), cart.subtotal());

    }

    @Test
    void should_throw_exception_when_product_not_active() {
        Cart cart = Cart.create(userId);
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.of(cart));
        when(catalogPort.findActiveProduct(productId)).thenReturn(Optional.empty());

        assertThrows(ProductNotFoundException.class, () -> addItemToCartUseCase.execute(new AddItemToCartCommandDTO(
                userId,
                productId.getId(),
                3
        )));
    }

    @Test
    void should_throw_exception_when_cart_not_found() {
        when(cartRepository.findActiveByUserId(userId)).thenReturn(Optional.empty());
        assertThrows(CartNotFoundOrINotActiveException.class, () -> addItemToCartUseCase.execute(new AddItemToCartCommandDTO(
                userId,
                productId.getId(),
                3
        )));
    }

}