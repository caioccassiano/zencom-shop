package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.responses;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserDetailResponseDTO(
        UUID userId,
        String email,
        Set<String> roles,
        Instant createdAt
) {
}
