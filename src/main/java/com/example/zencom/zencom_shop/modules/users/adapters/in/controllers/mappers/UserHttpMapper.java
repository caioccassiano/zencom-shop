package com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.mappers;

import com.example.zencom.zencom_shop.modules.users.adapters.in.controllers.dtos.requests.RegisterRequestDTO;
import com.example.zencom.zencom_shop.modules.users.application.dtos.input.RegisterUserCommandDTO;
import com.example.zencom.zencom_shop.modules.users.domain.enums.NotificationChannel;

public class UserHttpMapper {

    private UserHttpMapper() {}

    public static RegisterUserCommandDTO toCommand(RegisterRequestDTO requestDTO) {
        return new RegisterUserCommandDTO(
                requestDTO.email(),
                requestDTO.password(),
                toNotificationChannel(requestDTO.notificationChannel()),
                requestDTO.phoneNumber()
        );
    }

    private static NotificationChannel toNotificationChannel(String value) {
        try {
            return NotificationChannel.valueOf(value.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Invalid notification channel: " + value);
        }
    }
}
