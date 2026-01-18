package com.example.zencom.zencom_shop.modules.payments.application.handlers;

import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.application.resolvers.PaymentCreateGatewayResolver;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.contracts.events.payments.PaymentCreatedIntegrationEvent;

public class PaymentCreatedIntegrationEventHandler {

    private final PaymentRepository paymentRepository;
    private final PaymentCreateGatewayResolver gatewayResolver;

    public PaymentCreatedIntegrationEventHandler(PaymentRepository paymentRepository, PaymentCreateGatewayResolver gatewayResolver) {
        this.paymentRepository = paymentRepository;
        this.gatewayResolver = gatewayResolver;
    }

    public void handle(PaymentCreatedIntegrationEvent event) {
        Payment payment = paymentRepository.findByPaymentId(event.aggregateId())
                .orElseThrow(() -> new IllegalStateException("Payment not found"));

        if(payment.hasProviderPaymentId()) return;

        var gateway = gatewayResolver.resolve(event.provider());
        var result = gateway.createPayment(payment);

        payment.attachProviderPaymentId(result.providerReferenceId(), result.checkoutUrl());
        paymentRepository.save(payment);



    }
}
