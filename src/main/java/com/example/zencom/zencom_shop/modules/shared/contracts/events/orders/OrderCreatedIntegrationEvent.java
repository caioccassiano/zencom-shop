package com.example.zencom.zencom_shop.modules.shared.contracts.events.orders;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record OrderCreatedIntegrationEvent(
       EventMetadata metadata,
       Payload payload
) implements IntegrationEvent<OrderCreatedIntegrationEvent.Payload> {

    public static final String TYPE = "orders.order_created.v1";
    public static final String ROUTING_KEY = "order.created";

    public String routingKey() {
        return ROUTING_KEY;
    }

    public record Payload(
            UUID orderId,
            UUID userId,
            BigDecimal totalAmount,
            List<Item> items
    ){}

    public record Item(
            UUID productId,
            int quantity
    ){}

    public static OrderCreatedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID orderId,
            UUID userId,
            BigDecimal totalAmount,
            List<Item> items
    ){
        return new OrderCreatedIntegrationEvent(
                new EventMetadata(eventId, occurredAt,TYPE, correlationId),
                new Payload(orderId, userId, totalAmount, items)
        );
    }

}
