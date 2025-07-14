package com.example.scheduler.service;

import com.example.scheduler.dto.PersonDTO;
import com.example.scheduler.dto.PersonResponseDTO;
import com.example.scheduler.dto.MeetingResponseDTO;
import com.example.scheduler.exception.CustomException;
import com.example.scheduler.model.Person;
import com.example.scheduler.repository.PersonRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Service layer responsible for handling person-related operations such as
 * registration, listing, and retrieving individual schedules.
 */
@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final MeetingService meetingService;

    public PersonService(PersonRepository personRepository, MeetingService meetingService) {
        this.personRepository = personRepository;
        this.meetingService = meetingService;
    }

    /**
     * Registers a new person.
     * Validates uniqueness of email before creation.
     *
     * @param dto the person creation request
     * @return the created person response DTO
     */
    public PersonResponseDTO createPerson(PersonDTO dto) {
        // Check if email already exists
        if (personRepository.findByEmail(dto.getEmail()).isPresent()) {
            throw new CustomException("Email already exists: " + dto.getEmail());
        }

        // Save new person
        Person person = new Person(null, dto.getName(), dto.getEmail());
        person = personRepository.save(person);

        return new PersonResponseDTO(person.getId(), person.getName(), person.getEmail());
    }

    /**
     * Retrieves a list of all registered persons.
     *
     * @return list of person response DTOs
     */
    public List<PersonResponseDTO> getAllPersons() {
        return personRepository.findAll().stream()
                .map(p -> new PersonResponseDTO(p.getId(), p.getName(), p.getEmail()))
                .collect(Collectors.toList());
    }

    /**
     * Fetches the schedule (list of meetings) for a given person.
     *
     * @param personId the ID of the person
     * @return list of meeting response DTOs for that person
     */
    public List<MeetingResponseDTO> getScheduleForPerson(Long personId) {
        return meetingService.getScheduleForPerson(personId);
    }
}
