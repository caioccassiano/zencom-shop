package com.example.zencom.zencom_shop.modules.payments.application.usecases;

import com.example.zencom.zencom_shop.modules.payments.application.dtos.input.CreatePaymentCommandDTO;
import com.example.zencom.zencom_shop.modules.payments.application.dtos.output.PaymentResultDTO;
import com.example.zencom.zencom_shop.modules.payments.application.ports.PaymentRepository;
import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentStatus;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;
import com.example.zencom.zencom_shop.modules.shared.application.utils.IntegrationEventEmitter;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.UUID;

import static com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency.BRL;
import static com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider.STRIPE;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CreatePaymentUseCaseTest {

    private CreatePaymentUseCase useCase;
    private PaymentRepository repository;
    private IntegrationEventEmitter emitter;

    @BeforeEach
    void setUp() {
        repository = mock(PaymentRepository.class);
        emitter = mock(IntegrationEventEmitter.class);
        useCase = new CreatePaymentUseCase(repository, emitter);

    }
    @Test
    void should_create_payment() {
        UUID orderId = UUID.randomUUID();
        PaymentResultDTO result = useCase.execute(
                new CreatePaymentCommandDTO(
                        orderId,
                        new BigDecimal("100.00"),
                        "Stripe",
                        "BRL"
                )
        );
        assertNotNull(result);
        assertEquals(STRIPE.name(), result.provider());
        assertEquals(new BigDecimal("100.00"), result.amount());
        assertEquals(PaymentStatus.PENDING.name(), result.status());
        verify(repository).save(any(Payment.class));
        verify(emitter).emitFrom(any(Payment.class));
        verifyNoMoreInteractions(repository, emitter);
    }

    @Test
    void should_throw_exception_when_amois_null() {
        UUID orderId = UUID.randomUUID();
        assertThrows(InvalidInputException.class, () ->
             useCase.execute(
                new CreatePaymentCommandDTO(
                        orderId,
                        new BigDecimal("0"),
                        "Stripe",
                        "BRL"
                ))
        );

    }

    @Test
    void should_throw_exception_when_orderId_null() {
        assertThrows(InvalidInputException.class, () ->
                useCase.execute(
                        new CreatePaymentCommandDTO(
                                null,
                                new BigDecimal("150.00"),
                                "Stripe",
                                "BRL"
                        )
                ));
    }

}