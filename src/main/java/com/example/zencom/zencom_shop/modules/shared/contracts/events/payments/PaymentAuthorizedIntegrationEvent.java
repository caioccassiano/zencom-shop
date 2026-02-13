package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentAuthorizedIntegrationEvent(
        EventMetadata metadata,
        PaymentAuthorizedPayload payload

) implements IntegrationEvent<PaymentAuthorizedIntegrationEvent.PaymentAuthorizedPayload> {

    public final static String TYPE = "payments.payment_authorized.v1";
    public static final String ROUTING_KEY = "payment.authorized";

    public String routingKey() {
        return ROUTING_KEY;
    }

    public record PaymentAuthorizedPayload(
            UUID paymentId,
            UUID orderId
    ){}

    public static PaymentAuthorizedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID paymentId,
            UUID orderId
    ){
        return new PaymentAuthorizedIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new PaymentAuthorizedPayload(paymentId, orderId)
        );
    }
}
