package com.example.scheduler.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for sending meeting details in API responses.
 * Includes meeting ID, title, time window, and participant information.
 */
public class MeetingResponseDTO {

    private Long id;
    private String title;
    private LocalDateTime startTime;
    private LocalDateTime endTime;
    private List<PersonResponseDTO> participants;

    /**
     * Default constructor for deserialization.
     */
    public MeetingResponseDTO() {}

    /**
     * All-args constructor for creating a full meeting response.
     *
     * @param id           the meeting ID
     * @param title        the meeting title
     * @param startTime    the meeting start time
     * @param endTime      the meeting end time
     * @param participants the list of participants
     */
    public MeetingResponseDTO(Long id, String title, LocalDateTime startTime, LocalDateTime endTime, List<PersonResponseDTO> participants) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.participants = participants;
    }

    /** @return the unique ID of the meeting */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** @return the title or subject of the meeting */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the scheduled start time of the meeting */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** @return the scheduled end time of the meeting */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** @return the list of participants in the meeting */
    public List<PersonResponseDTO> getParticipants() {
        return participants;
    }

    public void setParticipants(List<PersonResponseDTO> participants) {
        this.participants = participants;
    }
}
