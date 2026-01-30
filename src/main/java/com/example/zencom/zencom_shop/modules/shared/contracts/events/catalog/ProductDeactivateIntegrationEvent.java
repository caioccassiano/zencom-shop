package com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record ProductDeactivateIntegrationEvent(
        EventMetadata metadata,
        ProductDeactivatedPayload payload


)implements IntegrationEvent<ProductDeactivateIntegrationEvent.ProductDeactivatedPayload> {

    public static final String TYPE = "products.product_deactivated.v1";

    public record ProductDeactivatedPayload(
            UUID productId
    ){}

    public static ProductDeactivateIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID productId
    ){
        return new ProductDeactivateIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new ProductDeactivatedPayload(productId)
        );
    }


}
