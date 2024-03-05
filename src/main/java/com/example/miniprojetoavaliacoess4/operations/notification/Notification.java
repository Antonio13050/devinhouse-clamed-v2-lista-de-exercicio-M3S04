package com.example.miniprojetoavaliacoess4.operations.notification;

import com.example.miniprojetoavaliacoess4.model.Person;

public interface Notification {
    void send(Person person, String content);
}
