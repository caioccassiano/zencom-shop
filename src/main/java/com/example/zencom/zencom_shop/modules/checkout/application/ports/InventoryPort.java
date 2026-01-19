package com.example.zencom.zencom_shop.modules.checkout.application.ports;

import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.InventoryReservationSnapshot;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory.ReservationRequest;

import java.util.UUID;

public interface InventoryPort {
    InventoryReservationSnapshot reserve(ReservationRequest request);
    void commit (UUID reservationId);
    void release(UUID reservationId);
}
