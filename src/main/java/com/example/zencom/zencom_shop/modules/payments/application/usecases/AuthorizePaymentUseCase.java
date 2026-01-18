package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.AuthorizePaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.PaymentId;

public class AuthorizePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final IntegrationEventEmitter emmitter;

    public AuthorizePaymentUseCase(
            PaymentRepository paymentRepository,
            IntegrationEventEmitter emitter
    ){
        this.paymentRepository = paymentRepository;
        this.emmitter = emitter;
    }

    public void execute(AuthorizePaymentCommandDTO command){
        Payment payment = paymentRepository.findByPaymentId(command.paymentId())
                .orElseThrow(() -> new PaymentNotFound(command.providerPaymentId()));
        payment.authorize(command.providerPaymentId());
        paymentRepository.save(payment);
        emmitter.emitFrom(payment);
    }
}
