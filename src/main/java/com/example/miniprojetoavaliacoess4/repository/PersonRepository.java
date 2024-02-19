package com.example.miniprojetoavaliacoess4.repository;

import com.example.miniprojetoavaliacoess4.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PersonRepository extends JpaRepository<Person, String> {

    Optional<Person> findByGuid(String guid);
    Optional<Person> findByEmail(String email);
}
