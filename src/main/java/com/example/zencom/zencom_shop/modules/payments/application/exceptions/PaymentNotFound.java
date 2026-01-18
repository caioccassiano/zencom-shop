package com.example.zencom.zencom_shop.modules.payments.application.exceptions;

import java.util.UUID;

public class PaymentNotFound extends RuntimeException {
    public PaymentNotFound(String providerReferenceId) {
        super("Payment not found with id: " + providerReferenceId);
    }
}
