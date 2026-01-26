package com.example.zencom.zencom_shop.modules.notification.application.dtos;

import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationChannel;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationType;

import java.util.UUID;

public record CreateNotificationCommandDTO(
        UUID eventId,
        UUID recipientUserId,
        String recipientAddress,
        String deduplicationKey,
        NotificationType type,
        NotificationChannel channel,
        String title,
        String body,
        UUID referenceId
) {
}
