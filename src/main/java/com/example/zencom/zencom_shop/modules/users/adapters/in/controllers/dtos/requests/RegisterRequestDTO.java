package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.requests;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public record RegisterRequestDTO(
        @JsonProperty("email")
        @NotBlank
        @Email
        String email,

        @JsonProperty("password")
        @NotBlank
        String password,

        @JsonProperty("notification_channel")
        @NotBlank
        String notificationChannel,

        @JsonProperty("phone_number")
        String phoneNumber


) {
}
