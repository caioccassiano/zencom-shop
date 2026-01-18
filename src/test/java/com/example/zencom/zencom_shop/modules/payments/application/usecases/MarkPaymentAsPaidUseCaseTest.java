package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsPaidCommandDTO;
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

import static com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider.ABACATEPAY;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class MarkPaymentAsPaidUseCaseTest {
    private  PaymentRepository repository;
    private  MarkPaymentAsPaidUseCase useCase;
    private IntegrationEventEmitter emitter;

    @BeforeEach
    void setUp() {
        repository = mock(PaymentRepository.class);
        emitter = mock(IntegrationEventEmitter.class);
        useCase = new MarkPaymentAsPaidUseCase(repository, emitter);
    }

    @Test
    void should_mark_payment_as_paid() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("120.00"),
                ABACATEPAY,
                PaymentCurrency.BRL
        );
        payment.attachProviderPaymentId("pi_123", null);
        when(repository.findByProviderId("pi_123"))
                .thenReturn(Optional.of(payment));
        useCase.execute(
                new MarkAsPaidCommandDTO(
                        "pi_123",
                        Instant.now()
                )
        );
        assertTrue(payment.isPaid());
        verify(repository).findByProviderId("pi_123");
        verify(repository).save(payment);
        verify(emitter).emitFrom(payment);
    }

    @Test
    void should_throw_exception_when_payment_not_found() {
        when(repository.findByProviderId("pi_123")).thenReturn(Optional.empty());
        assertThrows(PaymentNotFound.class,
                () -> useCase.execute(
                        new MarkAsPaidCommandDTO(
                                "pi_123",
                                Instant.now()
                        )
                ));

    }

}