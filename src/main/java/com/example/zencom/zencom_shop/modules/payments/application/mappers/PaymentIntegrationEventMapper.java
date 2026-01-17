package com.example.zencom.zencom_shop.modules.payments.application.mappers;

import com.example.zencom.zencom_shop.modules.payments.domain.events.PaymentCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.payments.PaymentCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;

public class PaymentIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    private final Map<Class<?>, Function<DomainEvent, IntegrationEvent>> mappings =
            Map.of(
                    PaymentCreatedIntegrationEvent.class,
                    event -> {

                        PaymentCreatedDomainEvent e = (PaymentCreatedDomainEvent) event;
                        return new PaymentCreatedIntegrationEvent(
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
