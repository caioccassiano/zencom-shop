package com.example.zencom.zencom_shop.modules.notification.application.ports;

import com.example.zencom.zencom_shop.modules.notification.domain.entities.Notification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface NotificationRepository {
    Optional<Notification> findById(UUID id);
    void save(Notification notification);

    //query the pending events ordering by created_at
    List<Notification> findPending(int limit);
    Optional<Notification> findByDeduplicationKey(String deduplicationKey);
}
