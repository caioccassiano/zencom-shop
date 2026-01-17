package com.example.zencom.zencom_shop.modules.payments.domain.entites;

import com.example.zencom.zencom_shop.modules.payments.domain.entities.Payment;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentStatus;
import com.example.zencom.zencom_shop.modules.payments.domain.events.PaymentCreatedDomainEvent;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidPaymentStateException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;
import java.util.UUID;

public class PaymentTest {
    private UUID orderId;
    private PaymentProvider paymentProvider;
    private PaymentCurrency paymentCurrency;
    private String providerId;


    @BeforeEach
    public void setUp() {
        orderId = UUID.randomUUID();
        paymentProvider = PaymentProvider.ABACATE_PAY;
        paymentCurrency = PaymentCurrency.BRL;
        providerId = UUID.randomUUID().toString();

    }

    @Test
    void should_create_payment() {
        Payment payment = Payment.create(
                orderId,
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );

        assertNotNull(payment);
        assertEquals(orderId, payment.getOrderId());
        assertEquals(paymentProvider, payment.getProvider());
        assertEquals(paymentCurrency, payment.getCurrency());
        assertEquals(PaymentStatus.PENDING, payment.getStatus());

        var events = payment.pullDomainEvents();
        assertNotNull(events);
        assertEquals(1, events.size());
        assertTrue(events.getFirst() instanceof PaymentCreatedDomainEvent);


    }

    @Test
    void should_raise_created_event_with_payment_id(){
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        var event = (PaymentCreatedDomainEvent) payment.pullDomainEvents().getFirst();
        assertNotNull(event);
        assertEquals(payment.getPaymentId().getId(), event.paymentId());
    }

    @Test
    void should_authorize_payment() {
        Payment payment = Payment.create(
                orderId,
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        payment.authorize(providerId);

        assertEquals(PaymentStatus.AUTHORIZED, payment.getStatus());
        assertEquals(providerId, payment.getProviderId());

    }

    @Test
    void should_throw_when_authorize_no_providerId() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        assertThrows(NullPointerException.class, () -> {
            payment.authorize(null);
        });
    }

    @Test
    void should_capture_payment() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        payment.authorize(providerId);
        payment.capture();

        assertEquals(PaymentStatus.PAID, payment.getStatus());
    }
    @Test
    void should_throw_when_capture_from_pending() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        assertThrows(InvalidPaymentStateException.class, () -> {
            payment.capture();
        });
    }

    @Test
    void should_cancel_payment() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        payment.cancel("Has not enough funds");
        assertEquals(PaymentStatus.CANCELED, payment.getStatus());;
    }

    @Test
    void should_throw_when_cancel_paid_order() {
        Payment payment = Payment.create(
                UUID.randomUUID(),
                new BigDecimal("123.50"),
                paymentProvider,
                paymentCurrency
        );
        payment.authorize(providerId);
        payment.capture();
        assertThrows(InvalidPaymentStateException.class, () -> {
            payment.cancel("Has not enough funds");
        });
    }

}


