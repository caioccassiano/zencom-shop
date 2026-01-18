package com.example.zencom.zencom_shop.modules.payments.domain.entities;

import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentCurrency;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentProvider;
import com.example.zencom.zencom_shop.modules.payments.domain.enums.PaymentStatus;
import com.example.zencom.zencom_shop.modules.payments.domain.events.*;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidPaymentStateException;
import com.example.zencom.zencom_shop.modules.shared.domain.AggrgateRoot;
import com.example.zencom.zencom_shop.modules.shared.ids.PaymentId;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Payment extends AggrgateRoot {
    private final PaymentId paymentId;
    private final UUID orderId;
    private PaymentStatus status;
    private final PaymentProvider provider;
    private final PaymentCurrency currency;
    private BigDecimal amount;
    private String providerPaymentId;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant paidAt;
    private Instant cancelledAt;
    private String cancelReason;
    private Instant failedAt;
    private String failedReason;
    private String checkoutUrl;

    private Payment(
            PaymentId paymentId,
            UUID orderId,
            PaymentStatus status,
            PaymentProvider provider,
            PaymentCurrency currency,
            BigDecimal amount,
            String providerPaymentId,
            Instant createdAt,
            Instant updatedAt,
            Instant paidAt,
            Instant canceledAt,
            String canceledReason,
            Instant failedAt,
            String failedReason,
            String checkoutUrl

    ){
        this.paymentId = Objects.requireNonNull(paymentId, "paymentId is null");
        this.orderId = Objects.requireNonNull(orderId, "orderId is null");
        this.status = Objects.requireNonNull(status, "status is null");
        this.provider = Objects.requireNonNull(provider, "provider is null");
        this.currency = Objects.requireNonNull(currency, "currency is null");
        this.amount = Objects.requireNonNull(amount, "amount is null");
        this.providerPaymentId = providerPaymentId;
        this.createdAt = Objects.requireNonNull(createdAt, "createdAt is null");
        this.updatedAt = Objects.requireNonNull(updatedAt, "updatedAt is null");
        this.paidAt = paidAt;
        this.cancelledAt = canceledAt;
        this.cancelReason = canceledReason;
        this.failedAt = failedAt;
        this.failedReason = failedReason;
        this.checkoutUrl = checkoutUrl;
    }

    public static Payment create(
            UUID orderId,
            BigDecimal amount,
            PaymentProvider provider,
            PaymentCurrency currency
    ){
        if(amount == null || amount.compareTo(BigDecimal.ZERO) <= 0) throw new InvalidInputException("amount is null or zero or negative");
        PaymentId paymentId = PaymentId.newId();
        Payment payment = new Payment(
                paymentId,
                orderId,
                PaymentStatus.PENDING,
                provider,
                currency,
                amount,
                null,
                Instant.now(),
                Instant.now(),
                null,
                null,
                null,
                null,
                null,
                null


        );
        // event raising
        payment.raise(PaymentCreatedDomainEvent.now(
                paymentId.getId(),
                payment.orderId,
                payment.provider.toString()
                ));
        return payment;
    }



    public void capture(){
        ensureStatus(PaymentStatus.AUTHORIZED);
        this.status = PaymentStatus.PAID;
        this.paidAt = Instant.now();
        raise(PaymentPaidDomainEvent.now(this.paymentId.getId()));
        this.touch();
    }

    public void markAsPaid(Instant paidAt){
        if(status != PaymentStatus.PENDING && status != PaymentStatus.AUTHORIZED){
            throw new InvalidPaymentStateException("Payment cannot be paid!");
        };
        this.status = PaymentStatus.PAID;
        this.paidAt = paidAt;
        raise(PaymentPaidDomainEvent.now(this.paymentId.getId()));
        this.touch();
    }

    public void cancel(String reason, Instant cancelledAt) {
        if (this.status == PaymentStatus.PAID
                || this.status == PaymentStatus.REFUND_PENDING
                || this.status == PaymentStatus.REFUNDED) {
            throw new InvalidPaymentStateException("cancel");
        }
        if (this.status == PaymentStatus.CANCELED) return;
        if (this.status == PaymentStatus.FAILED) {
            throw new InvalidPaymentStateException("cancel");
        }

        String normalized = normalizeReason(reason);
        if (normalized == null) {
            throw new IllegalArgumentException("cancel reason is required");
        }

        this.cancelledAt = cancelledAt;
        this.cancelReason = normalized;
        this.status = PaymentStatus.CANCELED;

        raise(PaymentCanceledDomainEvent.now(this.paymentId.getId(),this.cancelReason));
        this.touch();
    }

    public void attachProviderPaymentId(String providerPaymentId, String checkoutUrl){
        if(this.providerPaymentId != null){
            throw new InvalidPaymentStateException("provider is already attached");
        }
        this.providerPaymentId = providerPaymentId;
        this.checkoutUrl = checkoutUrl;
        touch();
    }


    public void authorize(String providerId){
        ensureStatus(PaymentStatus.PENDING);
        this.providerPaymentId = Objects.requireNonNull(providerId, "providerId is null");
        this.status = PaymentStatus.AUTHORIZED;
        raise(PaymentAuthorizedDomainEvent.now(this.paymentId.getId()));
        this.touch();
    }

    public void fail(String reason, Instant failedAt) {
        if (this.status == PaymentStatus.PAID
                || this.status == PaymentStatus.REFUND_PENDING
                || this.status == PaymentStatus.REFUNDED) {
            throw new InvalidPaymentStateException("fail");
        }
        if (this.status == PaymentStatus.FAILED) return;

        if (this.status == PaymentStatus.CANCELED) {
            throw new InvalidPaymentStateException("fail");
        }

        var normalized = normalizeReason(reason);
        if (normalized == null) {
            throw new IllegalArgumentException("fail reason is required");
        }

        this.failedAt = failedAt;
        this.failedReason = normalized;
        this.status = PaymentStatus.FAILED;

        PaymentFailedDomainEvent.now(this.paymentId.getId(), this.failedReason);
        this.touch();
    }


    public void refund(){
        ensureStatus(PaymentStatus.PAID);
        if(this.status == PaymentStatus.REFUNDED){
            throw  new InvalidPaymentStateException("Payment is already refunded");
        }
        this.status = PaymentStatus.REFUNDED;
        PaymentRefundedDomainEvent.now(this.paymentId.getId());
        this.touch();
    }

    private void touch(){
        this.updatedAt = Instant.now();
    }

    private void ensureStatus(PaymentStatus status){
        if(this.status != status){
            throw new InvalidPaymentStateException("Payment status is " + status);
        }
    }

    private static String normalizeReason(String reason) {
        if (reason == null) return null;
        var trimmed = reason.trim();
        return trimmed.isEmpty() ? null : trimmed;
    }

    public boolean isPaid(){
        return this.status == PaymentStatus.PAID;
    }
    public boolean isPending(){
        return this.status == PaymentStatus.PENDING;
    }
    public boolean isCancelled(){
        return this.status == PaymentStatus.CANCELED;
    }

    public boolean isRefunded(){
        return this.status == PaymentStatus.REFUNDED;
    }
    public boolean hasProviderPaymentId(){
        return this.providerPaymentId != null;
    }



    public PaymentId getPaymentId() {
        return paymentId;
    }

    public UUID getOrderId() {
        return orderId;
    }

    public PaymentStatus getStatus() {
        return status;
    }

    public PaymentProvider getProvider() {
        return provider;
    }

    public PaymentCurrency getCurrency() {
        return currency;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public String getProviderId() {
        return providerPaymentId;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public String getProviderPaymentId() {
        return providerPaymentId;
    }

    public Instant getPaidAt() {
        return paidAt;
    }

    public Instant getCancelledAt() {
        return cancelledAt;
    }

    public String getCancelReason() {
        return cancelReason;
    }

    public Instant getFailedAt() {
        return failedAt;
    }

    public String getFailedReason() {
        return failedReason;
    }
}
