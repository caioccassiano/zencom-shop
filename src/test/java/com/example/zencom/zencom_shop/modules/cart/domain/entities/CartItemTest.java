package com.example.zencom.zencom_shop.modules.cart.domain.entities;

import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


import java.math.BigDecimal;
import java.util.UUID;

class CartItemTest {

    private ProductId productId;

    @BeforeEach
    public void setUp() {

        productId = new ProductId(UUID.randomUUID());

    }

    @Test
    void should_create_a_cart_item() {
        CartItem cartItem = CartItem.create(
                ProductId.newId(),
                3,
                new BigDecimal("5.00"));
        assertNotNull(cartItem);
        assertEquals(3, cartItem.getQuantity());

    }

    @Test
    void should_increase_quantity() {
        CartItem cartItem = CartItem.create(
                ProductId.newId(),
                3,
                new BigDecimal("5.00")
        );
        assertEquals(3, cartItem.getQuantity());
        cartItem.increase(7);
        assertEquals(10, cartItem.getQuantity());
    }

    @Test
    void should_change_quantity() {
        CartItem cartItem = CartItem.create(
                ProductId.newId(),
                3,
                new BigDecimal("5.00")
        );
        assertEquals(3, cartItem.getQuantity());
        cartItem.changeQuantity(7);
        assertEquals(7, cartItem.getQuantity());
    }

    @Test
    void should_return_total(){
        CartItem cartItem = CartItem.create(
                ProductId.newId(),
                3,
                new BigDecimal("5.00")
        );
        assertEquals(new BigDecimal("15.00"), cartItem.total());
    }




}
