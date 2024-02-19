package com.example.miniprojetoavaliacoess4.model.transport;

import com.example.miniprojetoavaliacoess4.model.Book;
import com.example.miniprojetoavaliacoess4.model.Review;

public record ReviewDTO(String guid, PersonDTO person, int Rating) {
    public ReviewDTO(Review review){

        this(review.getGuid(), new PersonDTO(review.getPersonReview()), review.getRating());
    }
}
