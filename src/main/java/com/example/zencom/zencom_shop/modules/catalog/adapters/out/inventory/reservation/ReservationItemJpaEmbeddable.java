package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.reservation;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ReservationItemJpaEmbeddable {

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    public ReservationItemJpaEmbeddable(UUID productId, Integer quantity) {
        this.productId = productId;
        this.quantity = quantity;
    }

}
