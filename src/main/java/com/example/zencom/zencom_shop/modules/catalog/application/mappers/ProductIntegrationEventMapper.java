package com.example.zencom.zencom_shop.modules.catalog.application.mappers;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.product.Product;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductDeactivateDomainEvent;
import com.example.zencom.zencom_shop.modules.catalog.domain.events.ProductUpdatedDomainEvent;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductDeactivateIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.catalog.ProductUpdatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;

import java.util.Optional;
import java.util.UUID;

public class ProductIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    @Override
    public Optional<IntegrationEvent<?>> toIntegration(AggregateRoot aggregate, DomainEvent domainEvent, UUID correlationId) {
        if(!(aggregate instanceof Product product)) return Optional.empty();
        if(domainEvent instanceof ProductCreatedDomainEvent event){
            var payload = new ProductCreatedIntegrationEvent.ProductCreatedPayload(
                    product.getId().getId(),
                    product.getName(),
                    product.getPrice()
            );
            return Optional.of(new ProductCreatedIntegrationEvent(
                    new EventMetadata(event.eventId(), event.occurredAt(), ProductCreatedIntegrationEvent.TYPE, correlationId),
                    payload
            ));
        }
        else if (domainEvent instanceof ProductUpdatedDomainEvent event){
            var  payload = new ProductUpdatedIntegrationEvent.ProductUpdatedPayload(
                    product.getId().getId()
            );
            return Optional.of(new ProductUpdatedIntegrationEvent(
                    new EventMetadata(event.eventId(),
                            event.occurredAt(),
                            ProductUpdatedIntegrationEvent.TYPE,
                            correlationId),
                    payload
            ));
        }
        else if (domainEvent instanceof ProductDeactivateDomainEvent event){
            var payload = new ProductDeactivateIntegrationEvent.ProductDeactivatedPayload(
                    product.getId().getId()
            );
            return Optional.of(new ProductDeactivateIntegrationEvent(
                    new EventMetadata(event.eventId(),
                            event.occurredAt(),
                            ProductDeactivateIntegrationEvent.TYPE,
                            correlationId),
                    payload
            ));

        }
        return Optional.empty();
    }
}
