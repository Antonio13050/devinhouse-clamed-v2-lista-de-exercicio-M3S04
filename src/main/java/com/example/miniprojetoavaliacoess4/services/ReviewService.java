package com.example.miniprojetoavaliacoess4.services;

import com.example.miniprojetoavaliacoess4.repository.ReviewRepository;
import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.Book;
import com.example.miniprojetoavaliacoess4.model.Person;
import com.example.miniprojetoavaliacoess4.model.Review;
import com.example.miniprojetoavaliacoess4.model.transport.ReviewDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateReviewDTO;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

@Service
public class ReviewService {

    private final ReviewRepository reviewRepository;
    private final PersonService personService;
    private final BookService bookService;

    public ReviewService(ReviewRepository reviewRepository, PersonService personService, BookService bookService) {
        this.reviewRepository = reviewRepository;
        this.personService = personService;
        this.bookService = bookService;
    }

    @Transactional
    public ReviewDTO create(CreateReviewDTO body, UserDetails userInSession, String idBook) throws PersonNotFoundException, BookNotFoundException {
        Person person = this.personService.findByEmail(userInSession.getUsername());
        Book book = this.bookService.findByGuidBook(idBook);

        Review review = new Review(body, person, book);
        this.reviewRepository.save(review);
        book.getReviews().add(review);
        return new ReviewDTO(review);

    }

}
