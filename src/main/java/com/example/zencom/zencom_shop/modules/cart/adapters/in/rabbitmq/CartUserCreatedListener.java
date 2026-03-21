package com.example.zencom.zencom_shop.modules.cart.adapters.in.rabbitmq;

import com.example.zencom.zencom_shop.modules.cart.application.usecases.GetOrCreateCartUseCase;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.users.UserCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.infra.rabbitmq.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CartUserCreatedListener {

    private final GetOrCreateCartUseCase getOrCreateCartUseCase;

    @RabbitListener(queues = RabbitMqConfig.CART_QUEUE)
    public void onUserCreated(UserCreatedIntegrationEvent event) {
        handleUserCreated(event);
    }

    private void handleUserCreated(UserCreatedIntegrationEvent event) {
        var command = CartRabbitMapper.toGetOrCreateCartCommand(event);
        getOrCreateCartUseCase.execute(command);
    }
}
