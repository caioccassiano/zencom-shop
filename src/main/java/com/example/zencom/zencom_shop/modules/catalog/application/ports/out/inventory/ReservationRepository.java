package com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);
    Optional<Reservation> findById(UUID reservationId);
    Optional<Reservation> findByRequestId(String requestId);
    Optional<Reservation> findByCustomerId(UUID customerId);


}
