package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsPaidCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import com.example.zencom.zencom_shop.modules.shared.ids.PaymentId;

import java.util.UUID;

public class MarkPaymentAsPaidUseCase {

    private final PaymentRepository paymentRepository;
    private final IntegrationEventEmitter emitter;

    public MarkPaymentAsPaidUseCase(
            PaymentRepository paymentRepository,
            IntegrationEventEmitter emitter
    ){
        this.paymentRepository = paymentRepository;
        this.emitter = emitter;
    }
    public void execute(MarkAsPaidCommandDTO command) {
        Payment payment = paymentRepository.findByProviderId(command.providerPaymentId())
                .orElseThrow(() -> new PaymentNotFound(command.providerPaymentId()));
        payment.markAsPaid(command.paidAt());
        paymentRepository.save(payment);
        emitter.emitFrom(payment);
    }
}
