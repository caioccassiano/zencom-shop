package com.example.zencom.zencom_shop.modules.notification.application.usecases;

import com.example.zencom.zencom_shop.modules.notification.application.dtos.CreateNotificationCommandDTO;
import com.example.zencom.zencom_shop.modules.notification.application.exceptions.DuplicateDeduplicationException;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationRepository;
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

class CreateNotificationUseCaseTest {
    private  CreateNotificationUseCase createNotificationUseCase;
    private  NotificationRepository notificationRepository;

    String deduplicationKey = "deduplicationKey";
    UUID eventId = UUID.randomUUID();
    UUID recipientId = UUID.randomUUID();
    UUID referenceId = UUID.randomUUID();

    @BeforeEach
    void setUp() {
        notificationRepository = mock(NotificationRepository.class);
        createNotificationUseCase = new CreateNotificationUseCase(notificationRepository);
    }

    @Test
    void should_create_notification() {
        when(notificationRepository.findByDeduplicationKey(deduplicationKey)).thenReturn(Optional.empty());

        Notification result = createNotificationUseCase.execute(
                new CreateNotificationCommandDTO(
                        eventId,
                        recipientId,
                        "caio@email.com",
                        deduplicationKey,
                        NotificationType.ORDER_CREATED,
                        NotificationChannel.EMAIL,
                        "Order Created",
                        "Your order has been created",
                        referenceId

                )
        );
        assertNotNull(result);
        assertEquals(NotificationStatus.PENDING, result.getStatus());
        verify(notificationRepository, never()).findByDeduplicationKey(deduplicationKey);
        verify(notificationRepository, times(1)).save(result);
    }

    @Test
    void should_return_existing_notification_when_duplicate_key() {
        // arrange
        Notification existing = Notification.create(
                "dedupKey", NotificationType.ORDER_CREATED, NotificationChannel.EMAIL,
                "caio@email.com", UUID.randomUUID(), "Order Created", "Body"
        );

        doThrow(new DuplicateDeduplicationException("dedupKey", new RuntimeException("unique constraint")))
                .when(notificationRepository).save(any(Notification.class));

        when(notificationRepository.findByDeduplicationKey("dedupKey"))
                .thenReturn(Optional.of(existing));

        // act
        Notification result = createNotificationUseCase.execute(
                new CreateNotificationCommandDTO(
                        eventId,
                        recipientId,
                        "caio@email.com",
                        "dedupKey",
                        NotificationType.ORDER_CREATED,
                        NotificationChannel.EMAIL,
                        "title",
                        "body",
                        referenceId

                )
        );

        // assert
        assertNotNull(result);
        assertSame(existing, result);
        verify(notificationRepository, times(1)).save(any(Notification.class));
        verify(notificationRepository, times(1)).findByDeduplicationKey("dedupKey");
    }


}