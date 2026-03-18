package com.example.zencom.zencom_shop.modules.catalog.adapters.in.rabbitmq.reservation;

import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CancelReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation.CommitReservationCommandDTO;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderCanceledIntegrationEvent;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.orders.OrderPaidIntegrationEvent;

public class ReservationRabbitMapper {
    private  ReservationRabbitMapper() {
    }
    public static CancelReservationCommandDTO toCancelReservation(OrderCanceledIntegrationEvent event) {
        return new CancelReservationCommandDTO(
                event.payload().reservationId()
        );
    }
    public static CommitReservationCommandDTO toCommitReservation(OrderPaidIntegrationEvent event) {
        return new CommitReservationCommandDTO(
                event.payload().reservationId()
        );
    }
}

