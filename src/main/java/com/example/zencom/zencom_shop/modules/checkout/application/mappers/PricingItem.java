package com.example.zencom.zencom_shop.modules.checkout.application.mappers;

import java.math.BigDecimal;
import java.util.UUID;

public record PricingItem(
        UUID productId,
        int quantity,
        BigDecimal unitPrice,
        BigDecimal lineTotal
) {
}
