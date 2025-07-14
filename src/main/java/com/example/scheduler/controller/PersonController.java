package com.example.scheduler.controller;

import com.example.scheduler.dto.PersonDTO;
import com.example.scheduler.dto.PersonResponseDTO;
import com.example.scheduler.dto.MeetingResponseDTO;
import com.example.scheduler.service.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * REST controller for handling person-related operations such as creating persons,
 * listing all persons, and fetching a person's meeting schedule.
 */
@RestController
@RequestMapping("/api/persons")
public class PersonController {

    private final PersonService personService;

    /**
     * Constructor-based dependency injection for better testability and immutability.
     *
     * @param personService the service to handle person-related logic
     */
    @Autowired
    public PersonController(PersonService personService) {
        this.personService = personService;
    }

    /**
     * Creates a new person.
     *
     * @param dto the person details (name, email)
     * @return the created person's details
     */
    @PostMapping
    public PersonResponseDTO createPerson(@RequestBody PersonDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("Person details must not be null");
        }
        return personService.createPerson(dto);
    }

    /**
     * Retrieves a list of all registered persons.
     *
     * @return list of persons
     */
    @GetMapping
    public List<PersonResponseDTO> getAllPersons() {
        return personService.getAllPersons();
    }

    /**
     * Retrieves the meeting schedule for a given person by ID.
     *
     * @param id the ID of the person
     * @return list of meetings the person is part of
     */
    @GetMapping("/{id}/schedule")
    public List<MeetingResponseDTO> getSchedule(@PathVariable Long id) {
        if (id == null || id <= 0) {
            throw new IllegalArgumentException("Invalid person ID");
        }
        return personService.getScheduleForPerson(id);
    }
}
