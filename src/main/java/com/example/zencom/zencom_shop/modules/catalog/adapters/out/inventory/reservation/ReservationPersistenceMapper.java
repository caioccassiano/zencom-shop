package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.reservation;

import com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory.Reservation;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;

import java.util.List;

public class ReservationPersistenceMapper {
    private ReservationPersistenceMapper() {}

    public static Reservation toDomain(ReservationJpaEntity entity) {
        List<ReservationItem> items = entity.getItems().stream()
                .map(item -> new ReservationItem(
                        item.getProductId(),
                        item.getQuantity()
                )).toList();
        return Reservation.restore(
                entity.getReservationId(),
                entity.getRequestId(),
                entity.getCustomerId(),
                items,
                entity.getCreatedAt(),
                entity.getStatus()

        );
    }

    public static ReservationJpaEntity toEntity(Reservation reservation) {
        List<ReservationItemJpaEmbeddable> jpaItems =
                reservation.items()
                        .stream()
                        .map(item -> new ReservationItemJpaEmbeddable(
                                item.productId(),
                                item.quantity()
                        )).toList();
        return new ReservationJpaEntity(
                reservation.reservationId(),
                reservation.requestId(),
                reservation.customerId(),
                jpaItems,
                reservation.createdAt(),
                reservation.status()
        );

    }

}
