package com.example.miniprojetoavaliacoess4.repository;

import com.example.miniprojetoavaliacoess4.model.Review;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ReviewRepository extends JpaRepository<Review, String> {
    Optional<Review> findByGuid(String guid);
}
