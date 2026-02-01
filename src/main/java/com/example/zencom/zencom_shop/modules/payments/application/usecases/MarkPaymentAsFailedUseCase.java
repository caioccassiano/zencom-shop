package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsFailedCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;

import java.util.UUID;


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
        UUID requestId = UUID.fromString(payment.getRequestId());
        emitter.emitFrom(payment, requestId);
    }

}
