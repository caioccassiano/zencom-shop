package com.example.zencom.zencom_shop.modules.catalog.adapters.out.inventory.inventoryItem;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "inventory_item")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class InventoryItemJpaEntity {

    @Id
    @Column(name = "product_id", nullable = false, unique = true)
    private UUID productId;

    @Column(name = "available_quantity", nullable = false)
    private Integer availableQuantity;

    @Column(name = "reserved_quantity", nullable = false)
    private Integer reservedQuantity;

    @Column(name = "created_at", nullable = false)
    private Instant createdAt;

    @Column(name = "updated_at")
    private Instant updatedAt;

}
