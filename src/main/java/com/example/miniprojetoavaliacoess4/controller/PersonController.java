package com.example.miniprojetoavaliacoess4.controller;

import com.example.miniprojetoavaliacoess4.exceptions.InvalidNotificationTypeException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.transport.PersonDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import com.example.miniprojetoavaliacoess4.services.PersonService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.net.URI;

@RestController
@RequestMapping("/person")
public class PersonController {

    private PersonService personService;

    public PersonController(PersonService personService){
        this.personService = personService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<PersonDTO> findByGuid(@PathVariable("id") String guid) throws PersonNotFoundException {
        PersonDTO response = this.personService.findByGuid(guid);
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<PersonDTO> create(@Valid @RequestBody CreatePersonDTO body, UriComponentsBuilder uriComponentsBuilder) throws InvalidNotificationTypeException {
        PersonDTO response = this.personService.create(body);
        return ResponseEntity.created(uriComponentsBuilder.path("person/{id}").buildAndExpand(response.guid()).toUri()).body(response);
    }
}
