package com.example.scheduler.repository;

import com.example.scheduler.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

/**
 * Repository interface for performing CRUD operations on Person entities.
 */
public interface PersonRepository extends JpaRepository<Person, Long> {

    /**
     * Finds a person by their unique email address.
     *
     * @param email the email address to search for
     * @return an Optional containing the Person if found, or empty if not
     */
    Optional<Person> findByEmail(String email);
}
