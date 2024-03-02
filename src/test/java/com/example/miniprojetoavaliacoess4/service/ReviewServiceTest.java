package com.example.miniprojetoavaliacoess4.service;

import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Book;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.Review;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateReviewDTO;
import com.example.miniprojetoavaliacoess4.repository.ReviewRepository;
import com.example.miniprojetoavaliacoess4.services.BookService;
import com.example.miniprojetoavaliacoess4.services.PersonService;
import com.example.miniprojetoavaliacoess4.services.ReviewService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.Mockito.verify;

import java.util.UUID;

@ExtendWith(MockitoExtension.class)
class ReviewServiceTest {

    @Mock
    private PersonService personService;

    @Mock
    private BookService bookService;

    @Mock
    private ReviewRepository reviewRepository;

    @InjectMocks
    private ReviewService reviewService;

    @Captor
    private ArgumentCaptor<Review> reviewCaptor;

    @Test
    void createReviewReturnSuccess() throws PersonNotFoundException, BookNotFoundException {

        String email = "user@example.com";
        Person person = new Person();
        person.setEmail(email);

        BDDMockito.given(this.personService.findByEmail(email)).willReturn(person);

        String guid = UUID.randomUUID().toString();
        Book book = new Book();
        book.setGuid(guid);

        BDDMockito.given(this.bookService.findByGuidBook(guid)).willReturn(book);

        CreateReviewDTO form = new CreateReviewDTO(4);

        this.reviewService.create(form, person, book.getGuid());

        verify(this.reviewRepository).save(this.reviewCaptor.capture());
        Review createdReview = this.reviewCaptor.getValue();

        Assertions.assertEquals(form.rating(), createdReview.getRating());
        Assertions.assertNotNull(createdReview.getGuid());
        Assertions.assertEquals(36, createdReview.getGuid().length());

    }
}
