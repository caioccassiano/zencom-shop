package com.example.zencom.zencom_shop.modules.notification.application.ports;

import com.example.zencom.zencom_shop.modules.notification.application.ports.snapshots.UsersSnapshot;

import java.util.UUID;

public interface UsersPort {
    UsersSnapshot getUsersSnapshot(UUID userId);
}
