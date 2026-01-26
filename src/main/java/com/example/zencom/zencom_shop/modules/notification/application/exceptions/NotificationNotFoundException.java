package com.example.zencom.zencom_shop.modules.notification.application.exceptions;

import java.util.UUID;

public class NotificationNotFoundException extends RuntimeException {
    public NotificationNotFoundException(UUID notificationId) {
        super(String.valueOf(notificationId));
    }
}
