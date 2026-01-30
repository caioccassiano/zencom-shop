package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentCreatedIntegrationEvent(
        EventMetadata metadata,
        PaymentCreatedPayload payload

) implements IntegrationEvent<PaymentCreatedIntegrationEvent.PaymentCreatedPayload> {

    public final static String TYPE = "payments.payment_created.v1";

    public record PaymentCreatedPayload(
            UUID paymentId,
            String provider,
            BigDecimal amount,
            UUID orderId,
            String currency
    ){}

    public static PaymentCreatedIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID paymentId,
            String provider,
            BigDecimal amount,
            UUID orderId,
            String currency
    ){
        return new PaymentCreatedIntegrationEvent(
                new EventMetadata(eventId, occurredAt, TYPE, correlationId),
                new PaymentCreatedPayload(
                        paymentId,
                        provider,
                        amount,
                        orderId,
                        currency
                )
        );
    }


}
