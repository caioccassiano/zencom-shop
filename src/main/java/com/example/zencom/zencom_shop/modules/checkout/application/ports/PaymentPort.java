package com.example.zencom.zencom_shop.modules.checkout.application.ports;

import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.CreatePaymentRequest;
import com.example.zencom.zencom_shop.modules.checkout.application.snapshots.payments.PaymentCreatedSnapshot;

import java.util.UUID;

public interface PaymentPort {
    PaymentCreatedSnapshot createPayment(CreatePaymentRequest request);
    void cancel(UUID paymentId);
}
