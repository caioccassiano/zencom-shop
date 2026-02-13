package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

public record PaymentPaidIntegrationEvent(
        EventMetadata metadata,
        PaymentPaidPayload payload

) implements IntegrationEvent<PaymentPaidIntegrationEvent.PaymentPaidPayload> {

    public final static String TYPE = "payments.payment_paid.v1";
    public static final String ROUTING_KEY = "payment.paid";

    @Override
    public String routingKey() {
        return ROUTING_KEY;
    }

    public record PaymentPaidPayload(
            UUID paymentId,
            UUID orderId,
            BigDecimal amount,
            String provider,
            String currency,
            Instant paidAt
    ){}

    public static PaymentPaidIntegrationEvent now(
            UUID eventId,
            Instant occurredAt,
            UUID correlationId,
            UUID paymentId,
            UUID orderId,
            BigDecimal amount,
            String provider,
            String currency,
            Instant paidAt
    ){
        return new PaymentPaidIntegrationEvent(
                new EventMetadata(eventId,occurredAt, TYPE, correlationId ),
                new PaymentPaidPayload(
                        paymentId,
                        orderId,
                        amount,
                        provider,
                        currency,
                        paidAt
                )
        );
    }
}
