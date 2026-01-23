package com.example.zencom.zencom_shop.modules.inventory.application.ports;

import com.example.zencom.zencom_shop.modules.inventory.domain.entities.Reservation;
import com.example.zencom.zencom_shop.modules.shared.ids.ProductId;

import java.util.Optional;
import java.util.UUID;

public interface ReservationRepository {
    void save(Reservation reservation);
    Optional<Reservation> findById(UUID reservationId);
    Optional<Reservation> findByRequestId(String requestId);
    Optional<Reservation> findByCustomerId(UUID customerId);


}
