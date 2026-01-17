package com.example.zencom.zencom_shop.modules.payments.domain.enums;

import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;

public enum PaymentCurrency {
    BRL,
    USD,
    EUR,
    GBP;

    public static PaymentCurrency from(String value) {
        try {
            return PaymentCurrency.valueOf(value.toUpperCase());
        } catch (Exception e) {
            throw new InvalidInputException("Invalid currency: " + value);
        }
    }

    public static PaymentCurrency defaultCurrency() {
        return BRL;
    }
}
