package com.example.zencom.zencom_shop.modules.shared.contracts.events.payments;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;

import java.time.Instant;
import java.util.UUID;

public record PaymentCanceledIntegrationEvent(
        EventMetadata metadata,
        PaymentCanceledPayload payload

) implements IntegrationEvent<PaymentCanceledIntegrationEvent.PaymentCanceledPayload> {

  public final static String TYPE = "payments.payment_canceled.v1";

  public record PaymentCanceledPayload(
          UUID paymentId,
          UUID orderId,
          String reason,
          Instant canceledAt
  ){}

  public static PaymentCanceledIntegrationEvent now(
          UUID eventId,
          Instant occurredAt,
          UUID correlationId,
          UUID paymentId,
          UUID orderId,
          String reason,
          Instant canceledAt
  ){
      return new PaymentCanceledIntegrationEvent(
              new EventMetadata(eventId,occurredAt, TYPE, correlationId),
              new PaymentCanceledPayload(paymentId, orderId, reason, canceledAt)
      );
  }

}
