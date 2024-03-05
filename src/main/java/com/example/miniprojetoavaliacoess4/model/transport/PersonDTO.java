package com.example.miniprojetoavaliacoess4.model.transport;

import com.example.miniprojetoavaliacoess4.model.Person;

public record PersonDTO(String guid,
                        String email,
                        String name,
                        String phone) {

    public PersonDTO(Person person){
        this(person.getGuid(), person.getEmail(), person.getName(), person.getPhone());
    }
}
