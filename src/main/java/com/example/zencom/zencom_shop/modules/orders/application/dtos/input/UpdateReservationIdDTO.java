package com.example.zencom.zencom_shop.modules.orders.application.dtos.input;

import java.util.UUID;

public record UpdateReservationIdDTO(
        UUID orderId,
        UUID reservationId
) {
}
