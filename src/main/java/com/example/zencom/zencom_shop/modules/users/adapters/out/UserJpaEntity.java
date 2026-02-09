package com.example.zencom.zencom_shop.modules.users.adapters.out;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "users")
public class UserJpaEntity {

    @Id
    private UUID id;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(nullable = false, name = "passwordHash")
    private String passwordHash;

    @Column(nullable = false)
    private String role;

    @Column(name = "notification_channel", nullable = false)
    private String notificationChannel;

    private String phoneNumber;

    @Column(nullable = false)
    private Instant createdAt;

    private Instant updatedAt;

}
