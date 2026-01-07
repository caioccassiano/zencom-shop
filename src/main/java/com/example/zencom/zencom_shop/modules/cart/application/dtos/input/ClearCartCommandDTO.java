package com.example.zencom.zencom_shop.modules.cart.application.dtos.input;

import java.util.UUID;

public record ClearCartCommandDTO(
        UUID userId
) {
}


