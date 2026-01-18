package com.example.zencom.zencom_shop.modules.payments.application.mappers;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.output.PaymentResultDTO;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;

public final class PaymentResultMapper {
    private PaymentResultMapper() {}

    public static PaymentResultDTO toDto(Payment payment) {
        return new PaymentResultDTO(
                payment.getPaymentId().getId(),
                payment.getOrderId(),
                payment.getAmount(),
                payment.getCurrency().name(),
                payment.getProvider().name(),
                payment.getStatus().name()
        );
    }
}
