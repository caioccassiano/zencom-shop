package com.example.zencom.zencom_shop.modules.catalog.application.dtos.inventory.output;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;

import java.util.List;
import java.util.UUID;

public record InventoryReservationSnapshot(
        UUID reservationId,
        ReservationStatus status,
        List<ReservationItem> items
) {
}
