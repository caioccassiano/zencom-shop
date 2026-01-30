package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.users.UserCreatedIntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record ProductCreatedIntegrationEvent(
        EventMetadata metadata,
        ProductCreatedPayload payload

) implements IntegrationEvent<ProductCreatedIntegrationEvent.ProductCreatedPayload> {

    public static final String TYPE = "products.product_created.v1";

    public record ProductCreatedPayload(
            UUID productId,
            String productName,
            BigDecimal productPrice
    ){}

    public static ProductCreatedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID productId,
            String productName,
            BigDecimal productPrice
    ){
        return new ProductCreatedIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new ProductCreatedPayload(productId, productName, productPrice)
        );
    }
}

