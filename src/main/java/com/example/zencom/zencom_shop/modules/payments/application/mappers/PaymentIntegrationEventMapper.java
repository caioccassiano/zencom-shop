package com.example.zencom.zencom_shop.modules.payments.application.mappers;

import com.example.zencom.zencom_shop.modules.payments.domain.events.*;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.payments.*;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PaymentIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    private final Map<Class<?>, Function<DomainEvent, IntegrationEvent>> mappings =
            Map.of(
                    PaymentCreatedDomainEvent.class,
                    event -> {

                        PaymentCreatedDomainEvent e = (PaymentCreatedDomainEvent) event;
                        return new PaymentCreatedIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId(),
                                e.orderId(),
                                e.provider()
                        );
                    },
                    PaymentAuthorizedDomainEvent.class,
                    event -> {
                        PaymentAuthorizedDomainEvent e = (PaymentAuthorizedDomainEvent) event;
                        return new PaymentAuthorizedIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId()
                        );
                    },
                    PaymentPaidDomainEvent.class,
                    event -> {
                        PaymentPaidDomainEvent e = (PaymentPaidDomainEvent) event;
                        return new PaymentPaidIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId()
                        );
                    },
                    PaymentFailedDomainEvent.class,
                    event -> {
                        PaymentFailedDomainEvent e = (PaymentFailedDomainEvent) event;
                        return new PaymentFailedIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId(),
                                e.reason()
                        );
                    },
                    PaymentCanceledDomainEvent.class,
                    event -> {
                        PaymentCanceledDomainEvent e = (PaymentCanceledDomainEvent) event;
                        return new PaymentCanceledIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId(),
                                e.reason()
                        );
                    },
                    PaymentRefundedDomainEvent.class,
                    event -> {
                        PaymentRefundedDomainEvent e = (PaymentRefundedDomainEvent) event;
                        return new PaymentRefundedIntegrationEvent(
                                e.eventId(),
                                e.occurredAt(),
                                e.aggregateId()
                        );
                    }
            );

    @Override
    public Optional<IntegrationEvent> toIntegration(DomainEvent domainEvent) {
        Function<DomainEvent, IntegrationEvent> mapper = mappings.get(domainEvent.getClass());
        if (mapper == null) return Optional.empty();
        return Optional.of(
                mapper.apply(domainEvent));
    }

}
