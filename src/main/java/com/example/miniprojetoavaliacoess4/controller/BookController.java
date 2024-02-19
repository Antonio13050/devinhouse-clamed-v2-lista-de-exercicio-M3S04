package com.example.miniprojetoavaliacoess4.controller;

import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.exceptions.PersonNotFoundException;
import com.example.miniprojetoavaliacoess4.model.transport.BookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.BookListDTO;
import com.example.miniprojetoavaliacoess4.model.transport.ReviewDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateBookDTO;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateReviewDTO;
import com.example.miniprojetoavaliacoess4.services.BookService;
import com.example.miniprojetoavaliacoess4.services.ReviewService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

@RestController
@RequestMapping("/book")
public class BookController {

    private final BookService bookService;
    private final ReviewService reviewService;

    public BookController(BookService bookService, ReviewService reviewService){
        this.bookService = bookService;
        this.reviewService = reviewService;
    }

    @PostMapping
    public ResponseEntity<BookDTO> create(@AuthenticationPrincipal UserDetails userInSession,
                                          @Valid @RequestBody CreateBookDTO body,
                                          UriComponentsBuilder uriComponentsBuilder) throws PersonNotFoundException {
        BookDTO response = this.bookService.create(body, userInSession);
        return ResponseEntity.created(uriComponentsBuilder.path("/book/{id}").buildAndExpand(response.guid()).toUri()).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookDTO> findByGuid(@PathVariable("id") String id) throws BookNotFoundException {
        BookDTO response = this.bookService.findByGuid(id);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<List<BookListDTO>> listAll(){
        List<BookListDTO> response = this.bookService.listAll();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}/assessment")
    public ResponseEntity<ReviewDTO> createReview(@PathVariable("id") String id,
                                                  @Valid @RequestBody CreateReviewDTO body,
                                                  @AuthenticationPrincipal UserDetails userInSession,
                                                  UriComponentsBuilder uriComponentsBuilder) throws PersonNotFoundException, BookNotFoundException {
       ReviewDTO response = this.reviewService.create(body,userInSession,id);
       return ResponseEntity.created(uriComponentsBuilder.path("/book/{id}/assessment").buildAndExpand(response.guid()).toUri()).body(response);
    }

}
