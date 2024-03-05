package com.example.miniprojetoavaliacoess4.operations.notification.create;

import com.example.miniprojetoavaliacoess4.exceptions.InvalidNotificationTypeException;
import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import com.example.miniprojetoavaliacoess4.operations.notification.EmailNotification;
import com.example.miniprojetoavaliacoess4.operations.notification.Notification;
import com.example.miniprojetoavaliacoess4.operations.notification.SmsNotification;

public class NotificationFactory {

    private NotificationFactory() {

    }
    /**
     * Uso do padrão Factory Method
     */
    public static Notification createNotification(NotificationTypeEnum type)
            throws InvalidNotificationTypeException {

        return switch (type) {
            case EMAIL -> new EmailNotification();
            case SMS -> new SmsNotification();
            default -> throw new InvalidNotificationTypeException("Type is not valid: " + type);
        };
    }
}