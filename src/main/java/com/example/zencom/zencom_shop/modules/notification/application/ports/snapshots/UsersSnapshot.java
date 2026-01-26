package com.example.zencom.zencom_shop.modules.notification.application.ports.snapshots;

import java.util.UUID;

public record UsersSnapshot(
        UUID userId,
        String channel,
        String emailAddress,
        String phoneNumber
) {
}
