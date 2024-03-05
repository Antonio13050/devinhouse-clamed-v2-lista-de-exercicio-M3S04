package com.example.miniprojetoavaliacoess4.service;

import com.example.miniprojetoavaliacoess4.exceptions.InvalidNotificationTypeException;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.enums.NotificationTypeEnum;
import com.example.miniprojetoavaliacoess4.model.transport.PersonDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import com.example.miniprojetoavaliacoess4.repository.PersonRepository;
import com.example.miniprojetoavaliacoess4.services.PersonService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class PersonServiceTest {

    @InjectMocks
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Captor
    private ArgumentCaptor<Person> personCaptor;

    @Test
    void createUserOnDatabaseWithNoFail() throws InvalidNotificationTypeException {
        /* Arrange */
        CreatePersonDTO form =
                new CreatePersonDTO("user@test.com", "User 01", "48-99999-9999", "UmaSenhaForte", NotificationTypeEnum.EMAIL);
        String passwordEncoded = this.passwordEncoder.encode(form.password());

        /* Act*/
        this.personService.create(form);

        verify(this.personRepository).save(this.personCaptor.capture());
        Person createdPerson = this.personCaptor.getValue();

        /* Assertions */
        Assertions.assertEquals(form.name(), createdPerson.getName());
        Assertions.assertEquals(form.email(), createdPerson.getEmail());
        Assertions.assertNotNull(createdPerson.getGuid());
        Assertions.assertEquals(36, createdPerson.getGuid().length());
        Assertions.assertEquals(passwordEncoded, createdPerson.getPassword());
    }
}
