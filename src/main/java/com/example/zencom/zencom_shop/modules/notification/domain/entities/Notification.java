package com.example.zencom.zencom_shop.modules.notification.domain.entities;

import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationChannel;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationStatus;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationType;

import java.time.Instant;
import java.util.UUID;

public class Notification {
    private final UUID id;
    private final String idempotencyKey;
    private final NotificationType type;
    private final NotificationChannel channel;
    private final String recipient;
    private final String title;
    private final String body;
    private final UUID referenceId;
    private NotificationStatus status;
    private Integer attempts;
    private Instant createdAt;
    private Instant sentAt;
    private Instant failedAt;
    private Instant updatedAt;

    private Notification(
            UUID id,
            String idempotencyKey,
            NotificationType type,
            NotificationChannel channel,
            String recipient,
            String title,
            String body,
            UUID referenceId,
            NotificationStatus status,
            Integer attempts,
            Instant createdAt,
            Instant sentAt,
            Instant failedAt,
            Instant updatedAt

    ){
        if(id == null) throw new IllegalArgumentException("id cannot be null");
        if(idempotencyKey == null) throw new IllegalArgumentException("idempotencyKey cannot be null");
        if(type == null) throw new IllegalArgumentException("type cannot be null");
        if(channel == null) throw new IllegalArgumentException("channel cannot be null");
        if(recipient == null) throw new IllegalArgumentException("recipient cannot be null");
        if(title == null) throw new IllegalArgumentException("message cannot be null");
        if(body == null) throw new IllegalArgumentException("message cannot be null");
        if(status == null) throw new IllegalArgumentException("status cannot be null");
        if(attempts == null) throw new IllegalArgumentException("attempts cannot be null");
        if(createdAt == null) throw new IllegalArgumentException("createdAt cannot be null");


        this.id = id;
        this.idempotencyKey = idempotencyKey;
        this.type = type;
        this.channel = channel;
        this.recipient = recipient;
        this.title = title;
        this.body = body;
        this.referenceId = referenceId;
        this.status = status;
        this.attempts = attempts;
        this.createdAt = createdAt;
        this.sentAt = sentAt;
        this.failedAt = failedAt;
        this.updatedAt = updatedAt;

    }

    public static Notification create(
            String idempotencyKey,
            NotificationType type,
            NotificationChannel channel,
            String recipient,
            UUID referenceId,
            String title,
            String body
    ){
        return new Notification(
                UUID.randomUUID(),
                idempotencyKey,
                type,
                channel,
                recipient,
                title,
                body,
                referenceId,
                NotificationStatus.PENDING,
                1,
                Instant.now(),
                null,
                null,
                null
        );
    }

    public void markAsSent(){
        if(this.status == NotificationStatus.SENT) return;

        this.status = NotificationStatus.SENT;
        this.sentAt = Instant.now();
        this.failedAt = null;
        touch();
    }

    private void touch(){
        this.updatedAt = Instant.now();
    }

    public void markAsFailed() {
        if (this.status == NotificationStatus.SENT) {
            throw new IllegalStateException("Cannot fail a SENT notification");
        }
        this.status = NotificationStatus.FAILED;
        this.failedAt = Instant.now();
        touch();
    }

    public void increaseAttempt(){
        this.attempts++;
    }


    public boolean canRetry(int maxAttempts){
        return this.attempts < maxAttempts;
    }
    public boolean canSend(int maxAttempts) {
        if (this.status == NotificationStatus.SENT) return false;
        if (this.attempts >= maxAttempts) return false;
        return true; // PENDING ou FAILED
    }


    public UUID getId() {
        return id;
    }

    public String getIdempotencyKey() {
        return idempotencyKey;
    }

    public NotificationType getType() {
        return type;
    }

    public NotificationChannel getChannel() {
        return channel;
    }

    public String getRecipient() {
        return recipient;
    }

    public String getTitle() {
        return title;
    }
    public String getBody() {
        return body;
    }

    public NotificationStatus getStatus() {
        return status;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getSentAt() {
        return sentAt;
    }

    public Instant getFailedAt() {
        return failedAt;
    }

    public UUID getReferenceId() {
        return referenceId;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }
}
