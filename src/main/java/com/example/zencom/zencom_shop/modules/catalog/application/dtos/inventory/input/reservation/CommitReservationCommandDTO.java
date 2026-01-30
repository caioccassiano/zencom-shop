package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.input.reservation;

import java.util.UUID;

public record CommitReservationCommandDTO(
        UUID reservationId
) {
}
