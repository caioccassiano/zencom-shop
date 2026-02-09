package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record LoginRequestDTO(
        @JsonProperty("email")
        @NotBlank
        @Email
        String email,

        @JsonProperty
        @NotBlank
        String password
) {
}
