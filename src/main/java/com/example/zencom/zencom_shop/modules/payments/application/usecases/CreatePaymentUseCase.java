package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.CreatePaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.dtos.output.PaymentResultDTO;
import com.example.zencom.zencom_shop.modules.payments.application.mappers.PaymentResultMapper;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;

public class CreatePaymentUseCase {

    private final PaymentRepository paymentRepository;
    private final IntegrationEventEmitter emitter;


    public CreatePaymentUseCase(PaymentRepository paymentRepository,
                                IntegrationEventEmitter emitter) {
        this.paymentRepository = paymentRepository;
        this.emitter = emitter;
    }

    public PaymentResultDTO execute(CreatePaymentCommandDTO command) {
        validateCommand(command);
        PaymentCurrency currency = PaymentCurrency.from(command.currency());
        PaymentProvider provider = PaymentProvider.from(command.provider());
        Payment payment = Payment.create(
                command.orderId(),
                command.amount(),
                provider,
                currency
        );
        paymentRepository.save(payment);
        emitter.emitFrom(payment); //side effects
        return PaymentResultMapper.toDto(payment);

    }

    private void  validateCommand(CreatePaymentCommandDTO command) {
        if (command.amount()==null || command.amount().signum()<=0) throw new InvalidInputException("Amount must be greater than zero");
        if(command.orderId()==null) throw new InvalidInputException("Order ID cannot be null");
    }
}
