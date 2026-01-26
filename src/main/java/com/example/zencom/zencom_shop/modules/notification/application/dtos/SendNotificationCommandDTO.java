package com.example.zencom.zencom_shop.modules.notification.application.dtos;

import java.util.UUID;

public record SendNotificationCommandDTO(
        UUID notificationId
) {
}
