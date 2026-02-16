package com.example.zencom.zencom_shop.modules.shared.infra.rabbitmq;

import com.example.zencom.zencom_shop.modules.shared.contracts.events.EventMetadata;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.payments.PaymentPaidIntegrationEvent;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.JacksonJsonMessageConverter;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Configuration
public class RabbitMqConfig {

    // EXCHANGE
    public static final String EXCHANGE = "zencom.events";

    // QUEUES (each module has 1)

    public static final String CART_QUEUE = "cart.queue";
    public static final String NOTIFICATION_QUEUE = "notification.queue";
    public static final String CATALOG_QUEUE = "catalog.queue";
    public static final String ORDERS_QUEUE = "orders.queue";

    @Bean
    public TopicExchange zencomExchange() {
        return new TopicExchange(EXCHANGE, true, false);
    }

    @Bean
    public RabbitAdmin rabbitAdmin(ConnectionFactory connectionFactory) {
        RabbitAdmin admin = new RabbitAdmin(connectionFactory);
        admin.setAutoStartup(true);
        return admin;
    }
    @Bean
    public JacksonJsonMessageConverter jsonMessageConverter() {
        return new JacksonJsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory cf, JacksonJsonMessageConverter converter) {
        RabbitTemplate template = new RabbitTemplate(cf);
        template.setMessageConverter(converter);
        return template;
    }

    @Bean
    public ApplicationRunner testRabbit(RabbitTemplate rabbitTemplate) {
        return args -> {
            rabbitTemplate.convertAndSend(EXCHANGE, "payments.paid", new PaymentPaidIntegrationEvent(
                    new EventMetadata(
                            UUID.randomUUID(),
                            Instant.now(),
                            "payments",
                            UUID.randomUUID()
                    ),
                    new PaymentPaidIntegrationEvent.PaymentPaidPayload(
                            UUID.randomUUID(),
                            UUID.randomUUID(),
                            new BigDecimal("20.00"),
                            "ABACATE_PAY",
                            "BRL",
                            Instant.now()
                    )
            ));
            System.out.println("Message sent!");
        };
    }

    @Bean
    public Queue cartQueue() {
        return QueueBuilder.durable(CART_QUEUE).build();
    }
    @Bean
    public Queue notificationQueue() {
        return QueueBuilder.durable(NOTIFICATION_QUEUE).build();
    }
    @Bean
    public Queue catalogQueue() {
        return QueueBuilder.durable(CATALOG_QUEUE).build();
    }
    @Bean
    public Queue ordersQueue() {
        return QueueBuilder.durable(ORDERS_QUEUE).build();
    }

    //=====BINDINGS====

    // Cart listens to UserCreated
    @Bean
    public Binding cartOnUserCreated(TopicExchange integrationEventExchange, Queue cartQueue) {
        return BindingBuilder.bind(cartQueue).to(integrationEventExchange).with("user.created");
    }

    // Orders listens to Payment's events
    @Bean
    public Binding ordersOnPayments(TopicExchange integrationEventExchange, Queue ordersQueue) {
        return BindingBuilder.bind(ordersQueue).to(integrationEventExchange).with("payments.*");
    }

    // Catalog listens to all Order's events
    @Bean
    public Binding catalogOnOrders(TopicExchange integrationEventExchange, Queue catalogQueue) {
        return BindingBuilder.bind(catalogQueue).to(integrationEventExchange).with("orders.*");
    }

    // Notification listens to all Order's events
    @Bean
    public Binding notificationOnOrders(TopicExchange integrationEventExchange, Queue notificationQueue) {
        return BindingBuilder.bind(notificationQueue).to(integrationEventExchange).with("orders.*");
    }

    // Notification listens to Payment's events
    @Bean
    public Binding notificationOnPayments(TopicExchange integrationEventExchange, Queue notificationQueue) {
        return BindingBuilder.bind(notificationQueue).to(integrationEventExchange).with("payments.*");
    }

}
