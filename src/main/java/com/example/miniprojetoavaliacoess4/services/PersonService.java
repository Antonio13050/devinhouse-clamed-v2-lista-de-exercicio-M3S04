package com.example.miniprojetoavaliacoess4.services;

import com.example.miniprojetoavaliacoess4.exceptions.InvalidNotificationTypeException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.builders.PersonBuilder;
import com.example.miniprojetoavaliacoess4.model.transport.PersonDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import com.example.miniprojetoavaliacoess4.operations.notification.create.NotificationFactory;
import com.example.miniprojetoavaliacoess4.repository.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class PersonService implements UserDetailsService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    public PersonService (PersonRepository personRepository, PasswordEncoder passwordEncoder){
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private void sendNotification(Person person, String content) throws InvalidNotificationTypeException {
        NotificationFactory
                .createNotification(person.getNotificationType())
                .send(person, content);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("Usuário não encontrado" + username));
    }

    @Transactional
    public PersonDTO create(CreatePersonDTO createPersonDTO) throws InvalidNotificationTypeException {
        String passwordEncoded = this.passwordEncoder.encode(createPersonDTO.password());
        Person person = PersonBuilder.builder()
                .withName(createPersonDTO.name())
                .withPhone(createPersonDTO.phone())
                .withEmail(createPersonDTO.email())
                .withPassword(createPersonDTO.password())
                .withNotificationType(createPersonDTO.notificationType())
                .build();

        this.personRepository.save(person);
        this.sendNotification(person, "Cadastro de usuário");
        return new PersonDTO(person);
    }

    public PersonDTO findByGuid(String guid) throws PersonNotFoundException {
        return this.personRepository.findByGuid(guid)
                .map(PersonDTO::new)
                .orElseThrow(() -> new PersonNotFoundException("Usuário não encontrado"));
    }

    public Person findByEmail(String email) throws PersonNotFoundException {
        return this.personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException("Usuário não encontrado"));
    }

}
