package com.example.zencom.zencom_shop.modules.shared.contracts.events.orders;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record OrderCanceledIntegrationEvent(
        UUID eventId,
        Instant occurredAt,
        UUID orderId
) implements IntegrationEvent {

    public String eventType(){
        return "OrderCanceledIntegrationEvent";
    }

    public static OrderCanceledIntegrationEvent now(UUID orderId){
        return new OrderCanceledIntegrationEvent(
                UUID.randomUUID(),
                Instant.now(),
                orderId
        );
    }

}
