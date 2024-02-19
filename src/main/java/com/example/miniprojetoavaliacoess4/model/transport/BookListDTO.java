package com.example.miniprojetoavaliacoess4.model.transport;

import com.example.miniprojetoavaliacoess4.model.Book;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.stream.Collectors;

public record BookListDTO(String guid,
                          String title,
                          PersonDTO personRegistration,
                          LocalDateTime yearPublication,
                          double averageRating) {
    public BookListDTO(Book book) {
        this(book.getGuid(),
                book.getTitle(),
                new PersonDTO(book.getPersonRegistration()),
                book.getYearPublication(),
                book.calculateRating()
        );
    }
}
