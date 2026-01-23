package com.example.zencom.zencom_shop.modules.inventory.application.dtos.output;

import com.example.zencom.zencom_shop.modules.inventory.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.inventory.domain.vo.ReservationItem;

import java.util.List;
import java.util.UUID;

public record InventoryReservationSnapshot(
        UUID reservationId,
        ReservationStatus status,
        List<ReservationItem> items
) {
}
