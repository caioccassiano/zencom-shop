package com.example.zencom.zencom_shop.modules.users.application.mappers;

import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.users.UserCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.domain.AggregateRoot;
import com.example.zencom.zencom_shop.modules.shared.domain.events.DomainEvent;
import com.example.zencom.zencom_shop.modules.users.domain.entities.User;
import com.example.zencom.zencom_shop.modules.users.domain.events.UserCreatedDomainEvent;

import java.util.Optional;
import java.util.UUID;

public class UserIntegrationEventMapper implements IntegrationEventEmitter.DomainToIntegrationMapper {

    @Override
    public Optional<IntegrationEvent<?>> toIntegration(AggregateRoot aggregate, DomainEvent event, UUID correlationId) {
        if(!(aggregate instanceof User user)) return Optional.empty();
        if (event instanceof UserCreatedDomainEvent) {
            var payload = new UserCreatedIntegrationEvent.Payload(
                    user.getId().getId(),
                    user.getEmail(),
                    user.getChannel().toString(),
                    user.getRole().toString()
            );
            return Optional.of(new UserCreatedIntegrationEvent(
                    new EventMetadata(event.eventId(), event.occurredAt(), UserCreatedIntegrationEvent.TYPE,null),
                    payload
            ));
        }
        return Optional.empty();
    }
}
