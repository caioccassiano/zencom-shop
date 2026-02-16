package com.example.zencom.zencom_shop.modules.shared.infra.rabbitmq;

import com.example.zencom.zencom_shop.modules.shared.application.events.IntegrationEventPublisher;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.IntegrationEvent;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ZencomEventPublisher implements IntegrationEventPublisher {

    private final RabbitTemplate rabbitTemplate;

    public ZencomEventPublisher(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }

    @Override
    public void publish(List<IntegrationEvent<?>> events) {
        for (var event : events) {
            rabbitTemplate.convertAndSend(
                    RabbitMqConfig.EXCHANGE,
                    event.routingKey(),
                    event
            );
        }
    }
}
