package com.example.miniprojetoavaliacoess4.model.transport;

import com.example.miniprojetoavaliacoess4.model.Book;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record BookDTO(String guid,
                      String title,
                      PersonDTO personRegistration,
                      LocalDateTime yearPublication,
                      Set<ReviewDTO> reviews,
                      double averageRating
) {

    public BookDTO(Book book) {
        this(book.getGuid(),
                book.getTitle(),
                new PersonDTO(book.getPersonRegistration()),
                book.getYearPublication(),
                book.getReviews().stream().map(ReviewDTO::new).collect(Collectors.toSet()),
                book.calculateRating()
        );
    }

}
