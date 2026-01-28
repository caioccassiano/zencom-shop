package com.example.zencom.zencom_shop.modules.users.application.dtos.input;

public record LoginUserCommandDTO(
        String email,
        String password
) {
}
