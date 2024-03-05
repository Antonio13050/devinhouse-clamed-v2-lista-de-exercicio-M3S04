package com.example.miniprojetoavaliacoess4.model;

import com.example.miniprojetoavaliacoess4.model.builders.PersonBuilder;
import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import jakarta.persistence.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.Collections;
import java.util.UUID;

@Entity
public class Person implements UserDetails {

    public static void main(String[] args){
        Person person = PersonBuilder.builder()
                .withName("")
                .withPhone("")
                .withEmail("")
                .withPassword("")
                .withNotificationType(NotificationTypeEnum.EMAIL)
                .build();
    }

    @Id
    @Column(nullable = false, length = 36, unique = true)
    private String guid;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String phone;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    private boolean enabled;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private NotificationTypeEnum notificationType;

    public Person (){

    }

    public Person(String name, String phone, String email, String password, NotificationTypeEnum notificationType) {
        this.guid = UUID.randomUUID().toString();
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.password = password;
        this.notificationType = notificationType;
        this.enabled = true;
    }

    public Person(CreatePersonDTO createPersonDTO, String password){
        this.guid = UUID.randomUUID().toString();
        this.name = createPersonDTO.name();
        this.notificationType = createPersonDTO.notificationType();
        this.phone = createPersonDTO.phone();
        this.email = createPersonDTO.email();
        this.password = password;
        this.enabled = true;
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getName() {
        return name;
    }

    public NotificationTypeEnum getNotificationType() {
        return notificationType;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return Collections.emptyList();
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }
}
