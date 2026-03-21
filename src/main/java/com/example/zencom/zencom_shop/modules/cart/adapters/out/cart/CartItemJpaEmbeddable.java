package com.example.zencom.zencom_shop.modules.cart.adapters.out.cart;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.UUID;

@Embeddable
@Getter
@NoArgsConstructor
public class CartItemJpaEmbeddable {

    @Column(name = "product_id", nullable = false)
    private UUID productId;

    @Column(name = "quantity", nullable = false)
    private int quantity;

    @Column(name = "unit_price", nullable = false)
    private BigDecimal unitPrice;

    @Column(name = "added_at", nullable = false)
    private Instant addedAt;

    public CartItemJpaEmbeddable(UUID productId, int quantity, BigDecimal unitPrice, Instant addedAt) {
        this.productId = productId;
        this.quantity = quantity;
        this.unitPrice = unitPrice;
        this.addedAt = addedAt;
    }
}
