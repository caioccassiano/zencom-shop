package com.example.zencom.zencom_shop.modules.orders.domain.entities;

import com.example.zencom.zencom_shop.modules.orders.domain.enums.OrderStatus;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderApprovedDomainEvent;
import com.example.zencom.zencom_shop.modules.orders.domain.events.OrderCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.orders.domain.exceptions.*;
import com.example.zencom.zencom_shop.modules.shared.domain.AggrgateRoot;
import com.example.zencom.zencom_shop.modules.shared.ids.OrderId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public final class Order extends AggrgateRoot {
    private final OrderId orderId;
    private final UUID userId;
    private OrderStatus status;
    private final List<OrderItem> orderItems;
    private BigDecimal subtotal;
    private BigDecimal discountTotal;
    private BigDecimal total;
    private final Instant createdAt;
    private Instant updatedAt;

    private Order (
            OrderId orderId,
            UUID userId,
            List<OrderItem> orderItems,
            OrderStatus status,
            BigDecimal discountTotal,
            Instant createdAt,
            Instant updatedAt
            ) {
        if(orderId == null) {
            throw new OrderIdCannotBeNullException();
        }
        if(userId == null) throw new UserIdCannotBeNullException();
        if(orderItems == null || orderItems.isEmpty()) throw new OrderItemsCannotBeNullException();
        if(status == null) throw new StatusCannotBeNullException();
        if(createdAt == null) throw new IllegalArgumentException("createdAt cannot be null");


        this.orderId = orderId;
        this.userId = userId;
        this.orderItems = orderItems;
        this.status = status;
        this.discountTotal = discountTotal == null ? BigDecimal.ZERO : discountTotal;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;

        recalculateTotals();
        validateTotals();
    }

    //factory method
    public static Order create(UUID userId, List<OrderItem> orderItems) {
        Instant now = Instant.now();
        OrderId orderId = OrderId.newId();
        Order order = new Order(
                orderId,
                userId,
                orderItems,
                OrderStatus.PENDING,
                BigDecimal.ZERO,
                now,
                now);

        order.raise(OrderCreatedDomainEvent.now(
                orderId.getId(),
                userId
        ));

        return order;

    }
    //Behavior
    public void applyDiscount(BigDecimal discountTotal) {
        ensureNotFinalized();
        if(discountTotal == null || discountTotal.signum()<0) throw new DisccountInvalidException();

        this.discountTotal = discountTotal;
        touch();
    }

    private void touch() {
        this.updatedAt = Instant.now();
    }

    public void ensureNotFinalized() {
        if(this.status != OrderStatus.PENDING) {
            throw new OrderAlreadyFinalizedException();
        }
    }

    public void confirm(){
        ensureNotFinalized();
        this.status = OrderStatus.APPROVED;
        touch();
        raise(OrderApprovedDomainEvent.now(this.orderId.getId()));
    }

    public void cancel(){
        ensureNotFinalized();
        this.status = OrderStatus.CANCELED;
        touch();
    }



    //Internal Logic

    public void recalculateTotals(){
        this.subtotal = orderItems.stream()
                .map(OrderItem::subtotal)
                .reduce(BigDecimal.ZERO,BigDecimal::add);

        this.total = subtotal.subtract(this.discountTotal);
    }

    public void validateTotals(){
        if(total.signum()<0) throw new TotalCannotBeNullException();
    }

    public OrderId getOrderId() {
        return orderId;
    }
    public UUID getUserId() {
        return userId;
    }

    public OrderStatus getStatus() {
        return status;
    }

    public List<OrderItem> getOrderItems() {
        return List.copyOf(orderItems);
    }

    public BigDecimal getSubtotal() {
        return subtotal;
    }

    public BigDecimal getDiscountTotal() {
        return discountTotal;
    }

    public BigDecimal getTotal() {
        return total;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}


