package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.reservation;

import com.example.zencom.zencom_shop.modules.catalog.application.ports.out.inventory.ReservationRepository;
import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public class ReservationRepositoryAdapter implements ReservationRepository {

    private ReservationJpaRepository jpaRepository;
    public ReservationRepositoryAdapter(ReservationJpaRepository reservationJpaRepository) {
        this.jpaRepository = reservationJpaRepository;
    }


    @Override
    public void save(Reservation reservation) {
        ReservationJpaEntity entity = ReservationPersistenceMapper.toEntity(reservation);
        jpaRepository.save(entity);
    }

    @Override
    public Optional<Reservation> findById(UUID reservationId) {
        return jpaRepository.findByReservationId(reservationId)
                .map(ReservationPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Reservation> findByRequestId(String requestId) {
        return jpaRepository.findByRequestId(requestId)
                .map(ReservationPersistenceMapper::toDomain);
    }

    @Override
    public Optional<Reservation> findByCustomerId(UUID customerId) {
        return jpaRepository.findByCustomerId(customerId)
                .map(ReservationPersistenceMapper::toDomain);
    }
}
