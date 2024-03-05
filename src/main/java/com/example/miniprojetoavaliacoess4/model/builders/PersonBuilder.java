package com.example.miniprojetoavaliacoess4.model.builders;

import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import jakarta.persistence.Column;

public class PersonBuilder {


    private String name;
    private String phone;
    private String email;
    private String password;

    private NotificationTypeEnum notificationType;

    private PersonBuilder(){

    }

    public static PersonBuilder builder(){
        return new PersonBuilder();
    }

    public PersonBuilder withName(String name){
        this.name = name;
        return this;
    }

    public PersonBuilder withPhone(String phone){
        this.phone = phone;
        return this;
    }

    public PersonBuilder withEmail(String email){
        this.email = email;
        return this;
    }

    public PersonBuilder withPassword(String password){
        this.password = password;
        return this;
    }

    public PersonBuilder withNotificationType(NotificationTypeEnum notificationType){
        this.notificationType = notificationType;
        return this;
    }

    public Person build(){
        return new Person(this.name, this.phone, this.email, this.password, this.notificationType);
    }

}
