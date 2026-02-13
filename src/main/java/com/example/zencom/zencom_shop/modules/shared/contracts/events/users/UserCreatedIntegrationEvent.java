package com.example.zencom.zencom_shop.modules.shared.contracts.events.users;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCreatedIntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record UserCreatedIntegrationEvent (
    EventMetadata metadata,
    Payload payload
)implements IntegrationEvent<UserCreatedIntegrationEvent.Payload> {

    public static final String TYPE = "users.user_created.v1";
    public static final String ROUTING_KEY = "user.created";

    @Override
    public String routingKey() {
        return ROUTING_KEY;
    }
    public record Payload(
            UUID userId,
            String email,
            String channel,
            String role
    ){}

    public static UserCreatedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID userId,
            String email,
            String channel,
            String role
    ){
        return new UserCreatedIntegrationEvent(
                new EventMetadata(eventId, occurredAt,TYPE, null),
                new Payload(
                        userId,
                        email,
                        channel,
                        role
                )
        );
    }

}

