package com.example.zencom.zencom_shop.modules.checkout.application.snapshots.inventory;

import java.util.List;
import java.util.UUID;

public record ReservationRequest(
        String checkoutId,
        UUID costumerId,
        List<ReservationItem> items
) {
}
