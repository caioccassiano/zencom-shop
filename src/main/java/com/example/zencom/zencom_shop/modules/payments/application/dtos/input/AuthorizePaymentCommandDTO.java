package com.example.zencom.zencom_shop.modules.payments.application.dtos.input;

import java.util.UUID;

public record AuthorizePaymentCommandDTO(
        UUID paymentId,
        String providerPaymentId
) {
}
