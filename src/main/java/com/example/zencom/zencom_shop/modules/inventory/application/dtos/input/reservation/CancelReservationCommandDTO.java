package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.reservation;

import java.util.UUID;

public record CancelReservationCommandDTO(
        UUID reservationId
) {
}
