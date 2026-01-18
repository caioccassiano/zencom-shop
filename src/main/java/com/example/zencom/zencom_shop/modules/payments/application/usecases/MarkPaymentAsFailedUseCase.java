package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsFailedCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;


public class MarkPaymentAsFailedUseCase {
    private PaymentRepository paymentRepository;
    private IntegrationEventEmitter emitter;

    public MarkPaymentAsFailedUseCase(
            PaymentRepository paymentRepository,
            IntegrationEventEmitter emitter
    ) {
        this.paymentRepository = paymentRepository;
        this.emitter = emitter;
    }
    public void execute(MarkAsFailedCommandDTO command) {
        Payment payment = this.paymentRepository.findByProviderId(command.providerReferenceId())
                .orElseThrow(() -> new PaymentNotFound(command.providerReferenceId()));
        payment.fail(command.reason(), command.failedAt());
        paymentRepository.save(payment);
        emitter.emitFrom(payment);
    }

}
