package com.example.miniprojetoavaliacoess4.model;

import com.example.miniprojetoavaliacoess4.exceptions.BookNotFoundException;
import com.example.miniprojetoavaliacoess4.model.transport.operations.CreateBookDTO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Entity
public class Book {
    @Id
    @Column(nullable = false, length = 36, unique = true)
    private String guid;
    @Column(nullable = false)
    private String title;
    @ManyToOne
    @JoinColumn(name = "person_registration_guid", referencedColumnName = "guid", nullable = false)
    private Person personRegistration;
    @Column(nullable = false)
    private LocalDateTime yearPublication;
    @JsonIgnore
    @OneToMany
    @JoinTable(name = "reviews_to_book",
        joinColumns = @JoinColumn(name = "book_guid", referencedColumnName = "guid"),
        inverseJoinColumns = @JoinColumn(name = "reviews_guid", referencedColumnName = "guid"))
    private Set<Review> reviews = new HashSet<>();

    public Book(){
    }

    public Book(CreateBookDTO book, Person person){
        this.guid = UUID.randomUUID().toString();
        this.title = book.title();
        this.personRegistration = person;
        this.yearPublication = book.yearPublication();
    }

    public String getGuid() {
        return guid;
    }

    public void setGuid(String guid) {
        this.guid = guid;
    }

    public String getTitle() {
        return title;
    }

    public Person getPersonRegistration() {
        return personRegistration;
    }

    public LocalDateTime getYearPublication() {
        return yearPublication;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public double calculateRating(){
        double totalRating = this.getReviews()
                .stream().mapToDouble(Review::getRating)
                .sum();

        return totalRating / this.reviews.size();
    }
}
