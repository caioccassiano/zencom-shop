package com.example.zencom.zencom_shop.modules.users.application.dtos.input;

import com.example.zencom.zencom_shop.modules.users.domain.enums.NotificationChannel;

public record RegisterUserCommandDTO (
    String email,
    String password,
    NotificationChannel channel,
    String phoneNumber
    ){}
