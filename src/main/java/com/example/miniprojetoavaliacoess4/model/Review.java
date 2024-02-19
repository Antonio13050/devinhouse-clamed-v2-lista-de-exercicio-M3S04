package com.example.miniprojetoavaliacoess4.model;

import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateReviewDTO;
import jakarta.persistence.*;

import java.util.UUID;

@Entity
public class Review {
    @Id
    @Column(nullable = false, length = 36, unique = true)
    private String guid;
    @Column(nullable = false)
    private int rating;
    @ManyToOne
    @JoinColumn(name = "person_review_guid", referencedColumnName = "guid", nullable = false)
    private Person personReview;
    @ManyToOne
    @JoinColumn(name = "book_guid", referencedColumnName = "guid", nullable = false)
    private Book book;

    public Review() {
    }

    public Review(CreateReviewDTO review, Person person, Book book) {
        this.guid = UUID.randomUUID().toString();
        this.rating = review.rating();
        this.personReview = person;
        this.book = book;
    }

    public String getGuid() {
        return guid;
    }

    public int getRating() {
        return rating;
    }

    public Person getPersonReview() {
        return personReview;
    }

    public Book getBook() {
        return book;
    }
}
