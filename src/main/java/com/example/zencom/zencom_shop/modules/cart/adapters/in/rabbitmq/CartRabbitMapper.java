package com.example.zencom.zencom_shop.modules.cart.adapters.in.rabbitmq;

import com.example.zencom.zencom_shop.modules.cart.application.dtos.input.GetOrCreateCartCommandDTO;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.users.UserCreatedIntegrationEvent;

public class CartRabbitMapper {
    private CartRabbitMapper() {}

    public static GetOrCreateCartCommandDTO toGetOrCreateCartCommand(UserCreatedIntegrationEvent event) {
        return new GetOrCreateCartCommandDTO(event.payload().userId());
    }
}
