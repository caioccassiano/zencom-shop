package com.example.zencom.zencom_shop.modules.payments.application.ports;

import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.ids.PaymentId;

import java.util.Optional;
import java.util.UUID;

public interface PaymentRepository {
    Optional<Payment> findByUserId(UUID userId);
    Payment save(Payment payment);
    Optional<Payment> findByPaymentId(UUID paymentId);
    Optional<Payment>findByProviderId(String providerPaymentId);

}
