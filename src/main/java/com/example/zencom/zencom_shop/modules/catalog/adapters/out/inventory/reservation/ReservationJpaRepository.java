package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.reservation;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface ReservationJpaRepository extends JpaRepository<ReservationJpaEntity, UUID> {
    Optional<ReservationJpaEntity> findByReservationId(UUID id);

    Optional<ReservationJpaEntity> findByRequestId(String requestId);

    Optional<ReservationJpaEntity> findByCustomerId(UUID customerId);
}
