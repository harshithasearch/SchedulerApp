package com.example.scheduler.service;

import com.example.scheduler.dto.MeetingDTO;
import com.example.scheduler.dto.MeetingResponseDTO;
import com.example.scheduler.dto.PersonResponseDTO;
import com.example.scheduler.exception.CustomException;
import com.example.scheduler.model.Meeting;
import com.example.scheduler.model.Person;
import com.example.scheduler.repository.MeetingRepository;
import com.example.scheduler.repository.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * Service layer responsible for handling meeting-related operations such as
 * creation, scheduling checks, and availability suggestions.
 */
@Service
public class MeetingService {

    private final MeetingRepository meetingRepository;
    private final PersonRepository personRepository;

    @Autowired
    public MeetingService(MeetingRepository meetingRepository, PersonRepository personRepository) {
        this.meetingRepository = meetingRepository;
        this.personRepository = personRepository;
    }

    /**
     * Creates a new meeting with validation rules:
     * - Must start exactly on the hour
     * - Must include at least one participant
     * - No overlapping meeting for any participant at that time
     *
     * @param dto meeting details
     * @return the saved meeting in response DTO format
     */
    public MeetingResponseDTO createMeeting(MeetingDTO dto) {
        LocalDateTime startTime = dto.getStartTime();

        // 1. Validate exact hour start
        if (startTime.getMinute() != 0 || startTime.getSecond() != 0) {
            throw new CustomException("Meeting must start at the hour mark.");
        }

        // 2. Validate participants
        if (dto.getPersonIds() == null || dto.getPersonIds().isEmpty()) {
            throw new CustomException("Meeting must have at least one participant.");
        }

        // 3. Check for time conflicts
        List<Meeting> overlapping = meetingRepository.findByPersonsIdIn(dto.getPersonIds()).stream()
                .filter(m -> m.getStartTime().equals(startTime))
                .collect(Collectors.toList());

        if (!overlapping.isEmpty()) {
            throw new CustomException("One or more participants are already booked at this time.");
        }

        // 4. Fetch participants
        List<Person> participants = personRepository.findAllById(dto.getPersonIds());

        // 5. Create and save meeting
        Meeting meeting = new Meeting(null, dto.getTitle(), startTime, startTime.plusHours(1), participants);
        Meeting saved = meetingRepository.save(meeting);

        // 6. Map to response DTO
        return new MeetingResponseDTO(
                saved.getId(),
                saved.getTitle(),
                saved.getStartTime(),
                saved.getEndTime(),
                saved.getPersons().stream()
                        .map(p -> new PersonResponseDTO(p.getId(), p.getName(), p.getEmail()))
                        .collect(Collectors.toList())
        );
    }

    /**
     * Retrieves a list of meetings scheduled for a specific person.
     *
     * @param personId the ID of the person
     * @return list of MeetingResponseDTO
     */
    public List<MeetingResponseDTO> getScheduleForPerson(Long personId) {
        List<Meeting> meetings = meetingRepository.findByPersonsId(personId);

        return meetings.stream()
                .map(m -> new MeetingResponseDTO(
                        m.getId(),
                        m.getTitle(),
                        m.getStartTime(),
                        m.getEndTime(),
                        m.getPersons().stream()
                                .map(p -> new PersonResponseDTO(p.getId(), p.getName(), p.getEmail()))
                                .collect(Collectors.toList())
                ))
                .collect(Collectors.toList());
    }

    /**
     * Suggests the next 12 available 1-hour timeslots for a group of people.
     * Each slot starts exactly on the hour.
     *
     * @param personIds list of person IDs to check availability for
     * @return list of available LocalDateTime slots
     */
    public List<LocalDateTime> suggestTimeslots(List<Long> personIds) {
        // 1. Gather all meetings for the group
        List<Meeting> allMeetings = meetingRepository.findByPersonsIdIn(personIds);

        // 2. Build a set of busy start times
        Set<LocalDateTime> busySlots = allMeetings.stream()
                .map(Meeting::getStartTime)
                .collect(Collectors.toSet());

        // 3. Generate next 12 hour-mark slots starting from current hour
        LocalDateTime now = LocalDateTime.now().withMinute(0).withSecond(0).withNano(0);

        return IntStream.rangeClosed(1, 12)
                .mapToObj(i -> now.plusHours(i))
                .filter(slot -> !busySlots.contains(slot))
                .collect(Collectors.toList());
    }
}
