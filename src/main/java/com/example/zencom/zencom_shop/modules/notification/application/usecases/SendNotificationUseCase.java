package com.example.zencom.zencom_shop.modules.notification.application.usecases;

import com.example.zencom.zencom_shop.modules.notification.application.dtos.SendNotificationCommandDTO;
import com.example.zencom.zencom_shop.modules.notification.application.exceptions.NotificationNotFoundException;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationRepository;
import com.example.zencom.zencom_shop.modules.notification.application.ports.NotificationSender;
import com.example.zencom.zencom_shop.modules.notification.domain.entities.Notification;

public class SendNotificationUseCase {
    private final NotificationSender notificationSender;
    private final NotificationRepository notificationRepository;

    public SendNotificationUseCase(NotificationSender notificationSender, NotificationRepository notificationRepository) {
        this.notificationSender = notificationSender;
        this.notificationRepository = notificationRepository;
    }

    public void execute(SendNotificationCommandDTO command) {
        Notification notification = notificationRepository.findById(command.notificationId())
                .orElseThrow(()-> new NotificationNotFoundException(command.notificationId()));
        if(!notification.canSend(3)) return ;

        notification.increaseAttempt();
        notificationRepository.save(notification);

        try{
            notificationSender.send(notification);

            notification.markAsSent();
            notificationRepository.save(notification);
        }catch (Exception exception){
            notification.markAsFailed();
            notificationRepository.save(notification);
            throw  exception;
        }
    }
}
