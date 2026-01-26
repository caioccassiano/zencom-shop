package com.example.zencom.zencom_shop.modules.notification.application.usecases;

import com.example.zencom.zencom_shop.modules.notification.application.dtos.CreateNotificationCommandDTO;
import com.example.zencom.zencom_shop.modules.notification.application.exceptions.DuplicateDeduplicationException;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationRepository;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationSender;
import com.example.zencom.zencom_shop.modules.notification.domain.entities.Notification;
import com.example.zencom.zencom_shop.modules.payments.domain.exceptions.InvalidInputException;

import java.util.Optional;

public class CreateNotificationUseCase {
    private final NotificationRepository notificationRepository;

    public CreateNotificationUseCase(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public Notification execute(CreateNotificationCommandDTO command) {
        validateInput(command);
        Notification notification = Notification.create(
                command.deduplicationKey(),
                command.type(),
                command.channel(),
                command.recipientAddress(),
                command.referenceId(),
                command.title(),
                command.body()
        );
        try {
            notificationRepository.save(notification);
            return notification;
        } catch (DuplicateDeduplicationException exception) {
            return notificationRepository.
                    findByDeduplicationKey(command.deduplicationKey())
                    .orElseThrow(()->
                            new IllegalStateException("Deduplication key not found" + command.deduplicationKey()));
        }


    }


    private void validateInput(CreateNotificationCommandDTO command) {
        if (command.eventId() == null) throw new InvalidInputException("Event Id is required");
        if (command.recipientUserId() == null) throw new InvalidInputException("Recipient UserId is required");
        if (command.title() == null) throw new InvalidInputException("Title is required");
        if (command.body() == null) throw new InvalidInputException("Body is required");
        if (command.referenceId() == null) throw new InvalidInputException("Reference Id is required");

    }

    private Optional<Notification> checkDeduplicationKey(String deduplicationKey) {
        return notificationRepository.findByDeduplicationKey(deduplicationKey);
    }
}