package com.example.zencom.zencom_shop.modules.shared.contracts.events.orders;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

//OrderApprovedEvent in the domain layer
public record OrderPaidIntegrationEvent(
        EventMetadata metadata,
        OrderPaidPayload payload

) implements IntegrationEvent<OrderPaidIntegrationEvent.OrderPaidPayload> {

    public final static String TYPE = "orders.order_paid.v1";
    public static final String ROUTING_KEY = "order.paid";

    public String routingKey() {
        return ROUTING_KEY;
    }

    public record Item(
            UUID productId,
            int quantity
    ){}

    public record OrderPaidPayload(
            UUID orderId,
            UUID customerId,
            UUID reservationId,
            BigDecimal totalAmount,
            List<Item> items
    ){}

    public static OrderPaidIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID orderId,
            UUID customerId,
            UUID reservationId,
            BigDecimal totalAmount,
            List<Item> items
    ){
        return new OrderPaidIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new OrderPaidPayload(
                        orderId,
                        customerId,
                        reservationId,
                        totalAmount,
                        items
                )
        );
    }

}
