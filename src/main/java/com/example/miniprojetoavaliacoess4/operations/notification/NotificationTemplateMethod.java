package com.example.miniprojetoavaliacoess4.operations.notification;

import com.example.miniprojetoavaliacoess4.exceptions.InvalidNotificationTypeException;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.operations.notification.create.NotificationFactory;

public abstract class NotificationTemplateMethod {

    protected void notify(Person person, String content) throws InvalidNotificationTypeException {
        NotificationFactory
                .createNotification(person.getNotificationType())
                .send(person, content);
    }
}