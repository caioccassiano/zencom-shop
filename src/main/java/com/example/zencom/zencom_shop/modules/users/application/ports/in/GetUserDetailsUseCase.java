package com.example.zencom.zencom_shop.modules.users.application.ports.in;

import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;

import java.util.UUID;

public interface GetUserDetailsUseCase {

    UserResultDTO execute(UUID userId);
}
