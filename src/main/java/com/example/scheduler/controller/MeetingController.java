package com.example.scheduler.controller;

import com.example.scheduler.dto.MeetingDTO;
import com.example.scheduler.dto.MeetingResponseDTO;
import com.example.scheduler.dto.TimeslotSuggestionDTO;
import com.example.scheduler.service.MeetingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

/**
 * REST controller for handling meeting-related operations.
 */
@RestController
@RequestMapping("/api/meetings")
public class MeetingController {

    private final MeetingService meetingService;

    /**
     * Constructor-based injection for better testability and immutability.
     *
     * @param meetingService the meeting service
     */
    @Autowired
    public MeetingController(MeetingService meetingService) {
        this.meetingService = meetingService;
    }

    /**
     * Creates a new meeting based on the provided meeting data.
     *
     * @param dto the meeting details, including time and participants
     * @return the response DTO containing meeting info
     */
    @PostMapping
    public MeetingResponseDTO createMeeting(@RequestBody MeetingDTO dto) {
        // Basic validation can be added here or handled globally using @Valid
        if (dto == null) {
            throw new IllegalArgumentException("Meeting details must not be null");
        }
        return meetingService.createMeeting(dto);
    }

    /**
     * Suggests available 1-hour time slots that work for all the given participants.
     *
     * @param personIds list of participant IDs
     * @return DTO containing list of available time slots
     */
    @PostMapping("/suggest")
    public TimeslotSuggestionDTO suggestTimeslots(@RequestBody List<Long> personIds) {
        if (personIds == null || personIds.isEmpty()) {
            throw new IllegalArgumentException("Person IDs must not be null or empty");
        }

        List<LocalDateTime> suggestions = meetingService.suggestTimeslots(personIds);
        return new TimeslotSuggestionDTO(suggestions);
    }
}
