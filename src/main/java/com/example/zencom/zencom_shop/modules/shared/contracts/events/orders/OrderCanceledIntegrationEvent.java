package com.example.zencom.zencom_shop.modules.shared.contracts.events.orders;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCanceledIntegrationEvent(
      EventMetadata metadata,
      OrderCanceledPayload payload

) implements IntegrationEvent<OrderCanceledIntegrationEvent.OrderCanceledPayload> {

    public final static String TYPE = "orders.order_canceled.v1";
    public static final String ROUTING_KEY = "order.canceled";

    public String routingKey() {
        return ROUTING_KEY;
    }

    public record Item(
            UUID productId,
            int quantity
    ){}

    public record OrderCanceledPayload(
            UUID orderId,
            UUID customerId,
            BigDecimal totalAmount,
            List<Item> items,
            UUID reservationId

    ){}

    public static OrderCanceledIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID orderId,
            UUID customerId,
            BigDecimal totalAmount,
            List<Item> items,
            UUID reservationId
    ){
        return new OrderCanceledIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new OrderCanceledIntegrationEvent.OrderCanceledPayload(
                        orderId,
                        customerId,
                        totalAmount,
                        items,
                        reservationId
                )
        );
    }

}
