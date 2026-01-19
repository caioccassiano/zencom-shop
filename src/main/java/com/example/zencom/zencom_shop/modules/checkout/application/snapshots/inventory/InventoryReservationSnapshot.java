package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public record InventoryReservationSnapshot(
        UUID reservationId,
        ReservationStatus status,
        Instant expiresAt,
        List<ReservedItemSnapshot> items
) {
}
