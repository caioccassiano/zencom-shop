package com.example.zencom.zencom_shop.modules.orders.domain.entities;

import com.example.zencom.zencom_shop.modules.orders.domain.enums.OrderStatus;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.DisccountInvalidException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.OrderItemsCannotBeNullException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.TotalCannotBeNullException;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.UserIdCannotBeNullException;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class OrderTest {

    private UUID userId;
    private List<OrderItem> items;


    @BeforeEach
    void setUp() {
        userId = UUID.randomUUID();
        items = List.of(
                OrderItem.create(
                        ProductId.newId(),
                        "Product A",
                        new BigDecimal("10.00"),
                        2
                ),
                OrderItem.create(
                        ProductId.newId(),
                        "Product B",
                        new BigDecimal("5.00"),
                        1
                )
        );

    }
    @Test
    void should_create_order_when_valid(){
        Order order = Order.create(
                userId,
                items
        );
        assertNotNull(order);
        assertEquals(userId, order.getUserId());
        assertEquals(order.getOrderItems().getFirst(), items.getFirst());
        assertEquals(order.getStatus(), OrderStatus.PENDING);
        assertEquals(OrderId.class, order.getOrderId().getClass());

    }

    @Test
    void should_throw_when_userId_is_null(){
        assertThrows(UserIdCannotBeNullException.class, () -> Order.create(
                null,
                items
        ));
    }

    @Test
    void should_throw_when_items_is_null(){
        assertThrows(OrderItemsCannotBeNullException.class, () -> Order.create(
                userId,
                null
        ));
    }

    @Test
    void should_update_discountTotal_when_valid(){
        Order order = Order.create(
                userId,
                items
        );
        order.applyDiscount(new BigDecimal("2.00"));

        assertEquals(order.getDiscountTotal(),new BigDecimal("2.00"));
    }

    @Test
    void should_apply_discount_on_total(){
        Order order = Order.create(
                userId,
                items
        );
        order.applyDiscount(new BigDecimal("2.00"));
        order.recalculateTotals();
        assertEquals(order.getDiscountTotal(),new BigDecimal("2.00"));
        assertEquals(order.getTotal(),new BigDecimal("23.00"));
    }

    @Test
    void should_throw_when_discount_is_not_valid(){
        Order order = Order.create(
                userId,
                items
        );
        assertThrows(DisccountInvalidException.class, () -> order.applyDiscount(new BigDecimal("-2.00")));
        assertThrows(DisccountInvalidException.class, () -> order.applyDiscount(null));
    }

    @Test
    void should_change_status_to_approved(){
        Order order = Order.create(
                userId,
                items
        );
        order.confirm();
        assertEquals(OrderStatus.APPROVED, order.getStatus());
    }

    @Test
    void should_change_status_to_calcelled(){
        Order order = Order.create(
                userId,
                items
        );
        order.cancel();
        assertEquals(OrderStatus.CANCELED, order.getStatus());
    }

    @Test
    void should_throw_when_total_is_negative(){
        Order order = Order.create(
                userId,
                items
        );
        order.applyDiscount(new BigDecimal("55.00"));
        order.recalculateTotals();
        assertThrows(TotalCannotBeNullException.class, () -> order.validateTotals());
    }

}