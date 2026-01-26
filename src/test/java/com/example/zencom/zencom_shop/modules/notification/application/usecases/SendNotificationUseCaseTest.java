package com.example.zencom.zencom_shop.modules.notification.application.usecases;

import com.example.zencom.zencom_shop.modules.notification.application.dtos.SendNotificationCommandDTO;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationRepository;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationSender;
import com.example.zencom.zencom_shop.modules.notification.domain.entities.Notification;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationChannel;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationStatus;
import com.example.zencom.zencom_shop.modules.notification.domain.enums.NotificationType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class SendNotificationUseCaseTest {

    private SendNotificationUseCase sendNotificationUseCase;
    private NotificationRepository notificationRepository;
    private NotificationSender notificationSender;

    Notification notification = Notification.create(
            "deduplicationKey",
            NotificationType.ORDER_CREATED,
            NotificationChannel.EMAIL,
            "caio@email.com",
            UUID.randomUUID(),
            "title",
            "body"
    );

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        notificationSender = mock(NotificationSender.class);
        sendNotificationUseCase = new SendNotificationUseCase(notificationSender, notificationRepository);


    }
    @Test
    void should_send_notification() {
        when(notificationRepository.findById(notification.getId())).thenReturn(Optional.of(notification));
        doNothing().when(notificationSender).send(notification);

        sendNotificationUseCase.execute(
                new SendNotificationCommandDTO(
                        notification.getId()
                )
        );
        verify(notificationSender, times(1)).send(notification);
        verify(notificationRepository, atLeastOnce()).save(notification);

        assertEquals(NotificationStatus.SENT, notification.getStatus());

    }

    @Test
    void should_mark_as_failed_when_sender_throws() {
        UUID notificationId = UUID.randomUUID();
        Notification notification = Notification.create(
                "dedupKey",
                NotificationType.ORDER_CREATED,
                NotificationChannel.EMAIL,
                "caio@email.com",
                UUID.randomUUID(),
                "Order Created",
                "Your order has been created"
        );

        when(notificationRepository.findById(notificationId)).thenReturn(Optional.of(notification));
        doThrow(new RuntimeException("provider down")).when(notificationSender).send(notification);

        assertThrows(RuntimeException.class,
                () -> sendNotificationUseCase.execute(new SendNotificationCommandDTO(notificationId)));

        assertEquals(NotificationStatus.FAILED, notification.getStatus());
        verify(notificationRepository, atLeastOnce()).save(notification);
    }


}