package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentFailedIntegrationEvent(
      EventMetadata metadata,
      PaymentFailedPayload payload

) implements IntegrationEvent<PaymentFailedIntegrationEvent.PaymentFailedPayload> {

    public final static String TYPE = "payments.payment_failed.v1";
    public static final String ROUTING_KEY = "payment.failed";

    public String routingKey() {
        return ROUTING_KEY;
    }

    public record PaymentFailedPayload(
            UUID paymentId,
            UUID orderId,
            String reason,
            Instant failedAt
    ){}

    public static PaymentFailedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID paymentId,
            UUID orderId,
            String reason,
            Instant failedAt
    ){
        return new PaymentFailedIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new PaymentFailedPayload(paymentId,orderId, reason, failedAt)
        );
    }
}
