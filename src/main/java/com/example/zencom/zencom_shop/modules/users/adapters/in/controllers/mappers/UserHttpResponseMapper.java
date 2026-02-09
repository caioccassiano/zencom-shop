package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.mappers;

import com.example.zencom.zencom_shop.modules.shared.security.Role;
import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.responses.UserDetailResponseDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.output.UserResultDTO;

import java.util.Set;
import java.util.stream.Collectors;

public class UserHttpResponseMapper {
    private UserHttpResponseMapper() {}

    public static UserDetailResponseDTO toController(UserResultDTO dto) {
        Set<String> roles = dto.roles().stream()
                .map(Role::name)
                .collect(Collectors.toSet());
        return new UserDetailResponseDTO(
                dto.userId(),
                dto.email(),
                roles,
                dto.created_at()
        );
    }
}
