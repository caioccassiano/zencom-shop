package com.example.zencom.zencom_shop.modules.users.application.dtos.output;

import com.example.zencom.zencom_shop.modules.shared.security.Role;

import java.time.Instant;
import java.util.Set;
import java.util.UUID;

public record UserResultDTO(
        UUID userId,
        String email,
        Set<Role> roles,
        Instant created_at
) {
}
