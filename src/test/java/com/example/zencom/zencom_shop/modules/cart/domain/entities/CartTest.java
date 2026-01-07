package com.example.zencom.zencom_shop.modules.cart.domain.entities;

import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import com.example.zencom.zencom_shop.modules.cart.domain.exceptions.InvalidCartItemException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class CartTest {
    private  UUID userId;
    private  List<CartItem> items;

    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
    }

    @Test
    void should_create_cart_when_valid(){
        Cart cart = Cart.create(userId);
        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertEquals(CartStatus.ACTIVE, cart.getStatus());
    }

    @Test
    void should_add_item_when_valid(){
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                2,
                new BigDecimal("10.00")
        );
        assertNotNull(cart);
        assertEquals(userId, cart.getUserId());
        assertEquals(cart.getCartItemList().size(), 1);
        assertEquals(cart.getCartItemList().get(0).unitPrice, new BigDecimal("10.00"));


    }

    @Test
    void should_throw_when_cart_not_active(){
        Cart cart = Cart.create(userId);
        ProductId productId = ProductId.newId();
        cart.addNewItem(
                productId,
                2,
                new BigDecimal("10.00")

        );
        cart.checkout();
        assertThrows(InvalidCartItemException.class, () -> cart.updateQuantity(productId, 2));
    }

    @Test
    void should_remove_item_from_cart(){
        Cart cart = Cart.create(userId);
        ProductId productId1 = ProductId.newId();
        ProductId productId2 = ProductId.newId();
        cart.addNewItem(
                productId1,
                2,
                new BigDecimal("5.00")
                );
        cart.addNewItem(
                productId2,
                3,
                new BigDecimal("10.00")
        );
        assertEquals(cart.getCartItemList().size(), 2);

        cart.removeItem(productId1);
        assertEquals(cart.getCartItemList().size(), 1);
        assertEquals(cart.getCartItemList().get(0).unitPrice, new BigDecimal("10.00"));
    }

    @Test
    void should_clear_cart(){
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                2,
                new BigDecimal("10.00")
        );
        assertEquals(cart.getCartItemList().size(), 1);
        cart.clearCart();
        assertEquals(cart.getCartItemList().size(), 0);
    }

    @Test
    void should_abandon_cart(){
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                2,
                new BigDecimal("10.00")
        );
        assertEquals(cart.getStatus(), CartStatus.ACTIVE);
        cart.abandon();
        assertEquals(cart.getStatus(), CartStatus.ABANDONED);
    }

    @Test
    void should_return_subtotal(){
        Cart cart = Cart.create(userId);
        cart.addNewItem(
                ProductId.newId(),
                2,
                new BigDecimal("10.00")
        );
        cart.addNewItem(
                ProductId.newId(),
                5,
                new BigDecimal("100.00")
        );
        assertEquals(new BigDecimal("520.00"), cart.subtotal());
    }

    @Test
    void should_throw_when_productId_is_null(){
        Cart cart = Cart.create(userId);
        assertThrows(NullPointerException.class, () -> cart.FindItemOrNull(null));
    }

}