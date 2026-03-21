package com.example.zencom.zencom_shop.modules.cart.adapters.out.cart;

import com.example.zencom.zencom_shop.modules.cart.domain.enums.CartStatus;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "cart")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class CartJpaEntity {

    @Id
    @Column(name = "id", nullable = false, unique = true)
    private UUID id;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CartStatus status;

    @ElementCollection
    @CollectionTable(name = "cart_item", joinColumns = @JoinColumn(name = "cart_id"))
    private List<CartItemJpaEmbeddable> items;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at", nullable = false)
    private Instant updatedAt;
}
