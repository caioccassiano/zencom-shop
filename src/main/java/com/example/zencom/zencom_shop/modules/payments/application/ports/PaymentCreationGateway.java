package com.example.zencom.zencom_shop.modules.payments.application.ports;

import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;

public interface PaymentCreationGateway {
    String provider();
    PaymentCreationResult createPayment(Payment payment);


    public record PaymentCreationResult(String providerReferenceId, String checkoutUrl){}
}
