package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.reservation;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "reservation")
@Setter
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationJpaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID reservationId;

    @Column(name = "request_id", nullable = false)
    private String requestId;

    @Column(name = "customer_id",nullable = false)
    private UUID customerId;

    @ElementCollection
    @CollectionTable(
            name = "reservsation_item",
            joinColumns = @JoinColumn(name = "reservation_id")
    )
    private List<ReservationItemJpaEmbeddable> items = new ArrayList<>();

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private ReservationStatus status;

    public ReservationJpaEntity(
            UUID reservationId,
            String requestId,
            UUID customerId,
            List<ReservationItemJpaEmbeddable> items,
            Instant createdAt,
            ReservationStatus status
    ) {
        this.reservationId = reservationId;
        this.requestId = requestId;
        this.customerId = customerId;
        this.items = new ArrayList<>(items);
        this.createdAt = createdAt;
        this.status = status;
    }


}
