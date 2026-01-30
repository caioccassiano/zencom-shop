package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductUpdatedIntegrationEvent(
        EventMetadata metadata,
        ProductUpdatedPayload payload

) implements IntegrationEvent<ProductUpdatedIntegrationEvent.ProductUpdatedPayload> {

    public final static String TYPE = "products.product_updated.v1";

    public record ProductUpdatedPayload(
            UUID productId
    ){}

    public static ProductUpdatedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID productId
    ){
        return new ProductUpdatedIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new ProductUpdatedPayload(productId)
        );
    }
}
