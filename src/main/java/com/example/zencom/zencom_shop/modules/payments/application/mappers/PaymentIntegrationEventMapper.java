package com.example.zencom.zencom_shop.modules.payments.application.mappers;

import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.events.*;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.payments.*;
import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

public class PaymentIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    @Override
    public Optional<IntegrationEvent<?>> toIntegration(AggregateRoot aggregate, DomainEvent domainEvent, UUID correlationId){
        if(!(aggregate instanceof Payment payment)) return Optional.empty();
        if(domainEvent instanceof PaymentCreatedDomainEvent event) {
            var payload = new PaymentCreatedIntegrationEvent.PaymentCreatedPayload(
                    payment.getPaymentId().getId(),
                    payment.getProvider().toString(),
                    payment.getAmount(),
                    payment.getOrderId(),
                    payment.getCurrency().toString()
            );
            return Optional.of(new PaymentCreatedIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            PaymentCreatedIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));
        }
        if(domainEvent instanceof PaymentAuthorizedDomainEvent event){
            var payload = new PaymentAuthorizedIntegrationEvent.PaymentAuthorizedPayload(
                    payment.getPaymentId().getId(),
                    payment.getOrderId()
            );
            return Optional.of(new PaymentAuthorizedIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            PaymentAuthorizedIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));
        }
        if (domainEvent instanceof PaymentPaidDomainEvent event) {
            var payload = new PaymentPaidIntegrationEvent.PaymentPaidPayload(
                    payment.getPaymentId().getId(),
                    payment.getOrderId(),
                    payment.getAmount(),
                    payment.getProvider().toString(),
                    payment.getCurrency().toString(),
                    payment.getPaidAt()

            );
            return Optional.of(new PaymentPaidIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            PaymentPaidIntegrationEvent.TYPE,
                            correlationId),
                    payload
            ));
        }
        if(domainEvent instanceof PaymentCanceledDomainEvent event){
            var payload = new PaymentCanceledIntegrationEvent.PaymentCanceledPayload(
                    payment.getPaymentId().getId(),
                    payment.getOrderId(),
                    payment.getCancelReason(),
                    payment.getCancelledAt()
            );
            return Optional.of(new PaymentCanceledIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            PaymentCanceledIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));

        }
        if(domainEvent instanceof PaymentFailedDomainEvent event){
            var payload = new PaymentFailedIntegrationEvent.PaymentFailedPayload(
                    payment.getPaymentId().getId(),
                    payment.getOrderId(),
                    payment.getFailedReason(),
                    payment.getFailedAt()
            );
            return Optional.of(new PaymentFailedIntegrationEvent(
                    new EventMetadata(
                            event.eventId(),
                            event.occurredAt(),
                            PaymentFailedIntegrationEvent.TYPE,
                            correlationId
                    ),
                    payload
            ));
        }
        return Optional.empty();
    }

}
