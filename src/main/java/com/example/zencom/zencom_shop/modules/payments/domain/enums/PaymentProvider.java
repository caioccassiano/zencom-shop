package com.example.zencom.zencom_shop.modules.payments.domain.enums;

import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;

public enum PaymentProvider {
    ABACATEPAY,
    STRIPE;

    public static PaymentProvider from(String value) {
        try {
            return PaymentProvider.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid payment provider: " + value);
        }
    }
}


