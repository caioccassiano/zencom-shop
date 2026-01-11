package com.example.zencom.zencom_shop.modules.catalog.application.mappers;

import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductDeactivateDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductUpdatedDomainEvent;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductDeactivateIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductUpdatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Optional;

public class ProductIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    @Override
    public Optional<IntegrationEvent> toIntegration(DomainEvent domainEvent) {
        if(domainEvent instanceof ProductCreatedDomainEvent event){
            return Optional.of( new ProductCreatedIntegrationEvent(
                    event.eventId(),
                    event.occurredAt(),
                    event.aggregateId()
            ));
        }
        else if (domainEvent instanceof ProductUpdatedDomainEvent event){
            return Optional.of( new ProductUpdatedIntegrationEvent(
                    event.eventId(),
                    event.occurredAt(),
                    event.aggregateId()
            ));
        }
        else if (domainEvent instanceof ProductDeactivateDomainEvent event){
            return Optional.of( new ProductDeactivateIntegrationEvent(
                    event.eventId(),
                    event.occurredAt(),
                    event.aggregateId()
            ));
        }
        return Optional.empty();
    }
}
