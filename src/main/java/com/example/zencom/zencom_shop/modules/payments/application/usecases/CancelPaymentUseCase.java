package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.CancelPaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;

import java.util.UUID;

public class CancelPaymentUseCase {
    private final PaymentRepository paymentRepository;
    private final IntegrationEventEmitter emitter;

    public CancelPaymentUseCase(
            PaymentRepository paymentRepository,
            IntegrationEventEmitter emitter) {
        this.paymentRepository = paymentRepository;
        this.emitter = emitter;
    }
    public void execute(CancelPaymentCommandDTO command){
        Payment payment = paymentRepository.findByProviderId(command.providerReferenceId())
                .orElseThrow(() -> new PaymentNotFound(command.providerReferenceId()));
        payment.cancel(command.reason(), command.canceledAt());
        paymentRepository.save(payment);
        emitter.emitFrom(payment);
    }
}
