package com.example.zencom.zencom_shop.modules.checkout.application.dtos.input;

import com.example.zencom.zencom_shop.modules.checkout.application.dtos.PaymentMethod;

import java.util.UUID;

public record CreateCheckoutCommandDTO(
        UUID costumerId,
        PaymentMethod method, //ABACATEPAY or STRIPE
        String idempotencyKey
) {

}
