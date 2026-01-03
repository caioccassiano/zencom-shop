package com.example.zencom.zencom_shop.modules.orders.domain.entities;

import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ItemQuantityInvalidException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ItemUnitPriceInvalidException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ProductIdICannotBeNullException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.ProductNameInvalidException;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

class OrderItemTest {

    @Test
    void should_create_order_item_when_valid(){
        ProductId productId = ProductId.newId();

        OrderItem orderItem = OrderItem.create(
                productId,
                "Iphone 17 pro max",
                new BigDecimal("1499.90"),
                3
        );
        assertNotNull(orderItem);
        assertEquals(productId, orderItem.getProductId());
        assertEquals("Iphone 17 pro max", orderItem.getProductName());
        assertEquals(3, orderItem.getQuantity());
    }

    @Test
    void should_calculate_subtotal(){
        ProductId productId = ProductId.newId();
        OrderItem orderItem = OrderItem.create(
                productId,
                "Mac Air",
                new BigDecimal("1000.00"),
                3
        );

        assertEquals(new BigDecimal("3000.00"),orderItem.subtotal());
    }

    @Test
    void should_throw_when_quantity_is_zero_or_negative(){
        ProductId productId = ProductId.newId();

        assertThrows(ItemQuantityInvalidException.class, () -> OrderItem.create(
                productId,
                "Any",
                new BigDecimal("1000.00"),
                -2
        ));
        assertThrows(ItemQuantityInvalidException.class, () -> OrderItem.create(
                productId,
                "Any",
                new BigDecimal("1000.00"),
                0
        ));
    }

    @Test
    void should_throw_when_unity_price_is_zero_or_negative(){
        ProductId productId = ProductId.newId();
        assertThrows(ItemUnitPriceInvalidException.class, () -> OrderItem.create(
                productId,
                "Any",
                new BigDecimal("-1000.00"),
                2
        ));
        assertThrows(ItemUnitPriceInvalidException.class, () -> OrderItem.create(
                productId,
                "Any",
                new BigDecimal("0"),
                0
        ));
    }

    @Test
    void should_throw_when_product_name_is_blank(){
        ProductId productId = ProductId.newId();
        assertThrows(ProductNameInvalidException.class, () -> OrderItem.create(
                productId,
                "",
                new BigDecimal("1000.00"),
                2
        ));
    }

    @Test
    void should_throw_when_product_id_is_null(){
        ProductId productId = null;
        assertThrows(ProductIdICannotBeNullException.class, () -> OrderItem.create(
                productId,
                "Any",
                new BigDecimal("1000.00"),
                2
        ));
    }



}