package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.CancelPaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CancelPaymentUseCaseTest {

    private PaymentRepository repository;
    private IntegrationEventEmitter emitter;
    private CancelPaymentUseCase useCase;

    @BeforeEach
    void setUp() {
        repository = mock(PaymentRepository.class);
        emitter = mock(IntegrationEventEmitter.class);
        useCase = new CancelPaymentUseCase(repository, emitter);
    }

    @Test
    void should_cancel_payment(){
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("100.00"),
                PaymentProvider.STRIPE,
                PaymentCurrency.BRL
        );

        payment.attachProviderPaymentId("pi_123", null);

        when(repository.findByProviderId("pi_123"))
                .thenReturn(Optional.of(payment));

        useCase.execute(new CancelPaymentCommandDTO(
                "pi_123",
                "card_declined",
                Instant.now()
        ));

        assertTrue(payment.isCancelled());
        verify(repository).findByProviderId("pi_123");
        verify(repository).save(payment);
        verify(emitter).emitFrom(payment);
    }

    @Test
    void should_throw_exception_when_payment_not_found() {
            Payment payment = Payment.create(
                    UUID.randomUUID(),
                    new BigDecimal("100.00"),
                    PaymentProvider.STRIPE,
                    PaymentCurrency.BRL
            );

            payment.attachProviderPaymentId("pi_123", null);
        when(repository.findByProviderId("pi_123"))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(PaymentNotFound.class, () -> useCase.execute(
                new CancelPaymentCommandDTO("pi_123","card_declined", Instant.now())
        ));

        verify(repository).findByProviderId("pi_123");
        verify(repository, never()).save(any());
        verify(emitter, never()).emitFrom(any());
    }

}