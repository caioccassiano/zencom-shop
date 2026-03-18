package com.example.zencom.zencom_shop.modules.catalog.adapters.in.rabbitmq.reservation;

import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CancelReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CommitReservationUseCase;
import com.example.zencom.zencom_shop.modules.catalog.application.ports.in.inventory.CreateReservationUseCase;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCanceledIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCreatedIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderPaidIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.infra.rabbitmq.RabbitMqConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ReservationListener {
    private final CancelReservationUseCase cancelReservationUseCase;
    private final CommitReservationUseCase commitReservationUseCase;

    @RabbitListener(queues = RabbitMqConfig.CATALOG_ORDER_CANCELED_QUEUE)
    public void onOrderCanceled(OrderCanceledIntegrationEvent event) {
        handleOrderCanceled(event);
    }
    @RabbitListener(queues = RabbitMqConfig.CATALOG_ORDER_PAID_QUEUE)
    public void onOrderPaid(OrderPaidIntegrationEvent event) {
        handleOrderPaid(event);
    }


    /*Helper methods*/
    private void handleOrderCanceled(OrderCanceledIntegrationEvent event) {
        System.out.println("Order Canceled");
        var command = ReservationRabbitMapper.toCancelReservation(event);
        cancelReservationUseCase.execute(command);
    }
    private void handleOrderPaid(OrderPaidIntegrationEvent event) {
        System.out.println("Order Paid");
        var command = ReservationRabbitMapper.toCommitReservation(event);
        commitReservationUseCase.execute(command);
    }
}