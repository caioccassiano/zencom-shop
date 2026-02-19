package com.example.zencom.zencom_shop.modules.catalog.domain.entities.inventory;

import com.example.zencom.zencom_shop.modules.catalog.domain.enums.ReservationStatus;
import com.example.zencom.zencom_shop.modules.catalog.domain.vo.ReservationItem;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class Reservation {

    private final UUID reservationId;
    private final String requestId;
    private final UUID customerId;
    private final List<ReservationItem> items;
    private final Instant createdAt;
    private ReservationStatus status;

    private Reservation(
            UUID reservationId,
            String requestId,
            UUID customerId,
            List<ReservationItem> items,
            Instant createdAt,
            ReservationStatus status
    ) {
        if (reservationId == null) throw new IllegalArgumentException("reservationId is required");
        if (requestId.isBlank()) throw new IllegalArgumentException("requestId is required");
        if (customerId == null) throw new IllegalArgumentException("customerId is required");
        if (items == null || items.isEmpty()) throw new IllegalArgumentException("items are required");

        this.reservationId = reservationId;
        this.requestId = requestId;
        this.customerId = customerId;
        this.items = List.copyOf(items);
        this.createdAt = createdAt == null ? Instant.now() : createdAt;
        this.status = status == null ? ReservationStatus.RESERVED : status;
    }

    public static Reservation create(String requestId, UUID customerId, List<ReservationItem> items) {
        return new Reservation(
                UUID.randomUUID(),
                requestId,
                customerId,
                items,
                Instant.now(),
                ReservationStatus.RESERVED
        );
    }

    public static Reservation restore(
            UUID reservationId,
            String requestId,
            UUID customerId,
            List<ReservationItem> items,
            Instant createdAt,
            ReservationStatus status
    ){
        return new Reservation(
                reservationId,
                requestId,
                customerId,
                items,
                createdAt,
                status
        );
    }

    public UUID reservationId() { return reservationId; }
    public String requestId() { return requestId; }
    public UUID customerId() { return customerId; }
    public List<ReservationItem> items() { return items; }
    public Instant createdAt() { return createdAt; }
    public ReservationStatus status() { return status; }

    public void markAsCommitted() {
        ensureReserved();
        this.status = ReservationStatus.COMMITTED;
    }

    public void markAsReleased() {
        ensureReserved();
        this.status = ReservationStatus.RELEASED;
    }

    private void ensureReserved() {
        if (this.status != ReservationStatus.RESERVED) {
            throw new IllegalStateException("Reservation must be RESERVED to transition. Current=" + this.status);
        }
    }
}
