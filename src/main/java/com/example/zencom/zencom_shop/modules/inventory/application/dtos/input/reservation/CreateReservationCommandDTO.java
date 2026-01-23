package com.example.zencom.zencom_shop.modules.inventory.application.dtos.input.reservation;

import com.example.zencom.zencom_shop.modules.inventory.domain.vo.ReservationItem;

import java.util.List;
import java.util.UUID;

public record CreateReservationCommandDTO(
        String requestId,
        UUID customerId,
        List<ReservationItem> items
) {
}
