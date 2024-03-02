package com.example.miniprojetoavaliacoess4.service;

import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Book;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.transport.BookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.BookListDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateBookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreatePersonDTO;
import com.example.miniprojetoavaliacoess4.repository.BookRepository;
import com.example.miniprojetoavaliacoess4.repository.PersonRepository;
import com.example.miniprojetoavaliacoess4.services.BookService;
import com.example.miniprojetoavaliacoess4.services.PersonService;
import org.checkerframework.checker.units.qual.C;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private PersonService personService;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private BookService bookService;

    @Captor
    private ArgumentCaptor<Book> bookCaptor;

    @Test
    void createBookReturnsSuccess() throws PersonNotFoundException {
        /* Arrange */
        String email = "user@example.com";
        String userGuid = UUID.randomUUID().toString();

        Person person = new Person();
        person.setEmail(email);
        person.setGuid(userGuid);

        BDDMockito.given(this.personService.findByEmail(email)).willReturn(person);

        LocalDateTime data = LocalDateTime.now();
        CreateBookDTO form = new CreateBookDTO("Titulo 01", data);

        /* Act*/
        this.bookService.create(form, person);

        verify(this.bookRepository).save(this.bookCaptor.capture());
        Book createdBook = this.bookCaptor.getValue();

        /* Assertions */
        Assertions.assertEquals(form.title(), createdBook.getTitle());
        Assertions.assertEquals(person.getEmail(), createdBook.getPersonRegistration().getEmail());
        Assertions.assertNotNull(createdBook.getTitle());
    }

    @Test
    void listBookReturnsSuccess() throws PersonNotFoundException {
        String email = "user@example.com";
        String userGuid = UUID.randomUUID().toString();

        Person person = new Person();
        person.setEmail(email);
        person.setGuid(userGuid);

        LocalDateTime data = LocalDateTime.now();
        CreateBookDTO form01 = new CreateBookDTO("Titulo 01", data);
        CreateBookDTO form02 = new CreateBookDTO("Titulo 02", data);

        Book book01 = new Book(form01, person);
        Book book02 = new Book(form02, person);

        List<Book> fakeBookList = Arrays.asList(book01, book02);

        BDDMockito.given(this.bookRepository.findAll()).willReturn(fakeBookList);

        /* Act*/
        List<BookListDTO> result = this.bookService.listAll();

        verify(this.bookRepository).findAll();

        Assertions.assertEquals(2, result.size());
    }

    @Test
    void findByGuidBookWhenBookIsfound() throws BookNotFoundException {
        String email = "user@example.com";
        String userGuid = UUID.randomUUID().toString();

        Person person = new Person();
        person.setEmail(email);
        person.setGuid(userGuid);

        LocalDateTime data = LocalDateTime.now();
        CreateBookDTO form = new CreateBookDTO("Titulo 01", data);

        Book book = new Book(form, person);
        String id = book.getGuid();
        String exceptionMessage = "Book by id not found: " + id;

        BDDMockito.given(this.bookRepository.findByGuid(id)).willReturn(Optional.of(book));

        /* Act & Assert */
        Assertions.assertDoesNotThrow(
                () -> this.bookService.findByGuidBook(id), exceptionMessage);

    }
}
