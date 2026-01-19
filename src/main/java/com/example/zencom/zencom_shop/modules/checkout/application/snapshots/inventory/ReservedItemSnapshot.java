package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory;

import java.util.UUID;

public record ReservedItemSnapshot(
        UUID productId,
        int quantity
) {
}
