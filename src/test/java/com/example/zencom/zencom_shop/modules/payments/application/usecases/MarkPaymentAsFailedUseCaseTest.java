package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.MarkAsFailedCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.exceptions.PaymentNotFound;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentStatus;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class MarkPaymentAsFailedUseCaseTest {
    private PaymentRepository repository;
    private IntegrationEventEmitter emitter;
    private MarkPaymentAsFailedUseCase useCase;
    @BeforeEach
    void setUp() {
        repository = Mockito.mock(PaymentRepository.class);
        emitter = Mockito.mock(IntegrationEventEmitter.class);
        useCase = new MarkPaymentAsFailedUseCase(repository, emitter);
    }
    @Test
    void should_mark_payment_as_failed() {
        UUID orderId = UUID.randomUUID();
        Payment payment = Payment.create(
                orderId,
                new BigDecimal("1250.00"),
                PaymentProvider.ABACATEPAY,
                PaymentCurrency.BRL
        );
        payment.attachProviderPaymentId("pi-123", null);

        when(repository.findByProviderId("pi-123"))
                .thenReturn(Optional.of(payment));
        useCase.execute(
                new MarkAsFailedCommandDTO(
                        "pi-123",
                        "User has not enough funds",
                        Instant.now()
                )
        );
        assertEquals(PaymentStatus.FAILED, payment.getStatus());
        assertEquals("User has not enough funds", payment.getFailedReason());
        assertNotNull(payment.getFailedAt());
    }

    @Test
    void should_throw_exception_when_payment_not_found() {
        UUID orderId = UUID.randomUUID();
        Payment payment = Payment.create(
                orderId,
                new BigDecimal("1250.00"),
                PaymentProvider.ABACATEPAY,
                PaymentCurrency.BRL
        );
        payment.attachProviderPaymentId("pi-123", null);
        when(repository.findByProviderId("pi_123"))
                .thenReturn(Optional.empty());

        // act + assert
        assertThrows(PaymentNotFound.class, () -> useCase.execute(
                new MarkAsFailedCommandDTO("pi_123","card_declined", Instant.now() )
        ));

        verify(repository).findByProviderId("pi_123");
        verify(repository, never()).save(any());
        verify(emitter, never()).emitFrom(any());
    }
}
