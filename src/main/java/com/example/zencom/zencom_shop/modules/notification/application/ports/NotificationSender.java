package com.example.zencom.zencom_shop.modules.notification.application.ports;

import com.example.zencom.zencom_shop.modules.notification.domain.entities.Notification;

public interface NotificationSender {
    void send(Notification notification);
}
