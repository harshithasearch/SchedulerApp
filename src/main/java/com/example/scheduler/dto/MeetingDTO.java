package com.example.scheduler.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for creating a meeting.
 * Includes meeting title, start time, and participant IDs.
 */
public class MeetingDTO {

    private String title;
    private LocalDateTime startTime;
    private List<Long> personIds;

    /**
     * Default constructor for serialization/deserialization.
     */
    public MeetingDTO() {}

    /**
     * Parameterized constructor for manual creation.
     *
     * @param title     the title of the meeting
     * @param startTime the scheduled start time of the meeting
     * @param personIds the list of participant person IDs
     */
    public MeetingDTO(String title, LocalDateTime startTime, List<Long> personIds) {
        this.title = title;
        this.startTime = startTime;
        this.personIds = personIds;
    }

    /**
     * @return the meeting title
     */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return the scheduled start time
     */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /**
     * @return the list of person IDs participating in the meeting
     */
    public List<Long> getPersonIds() {
        return personIds;
    }

    public void setPersonIds(List<Long> personIds) {
        this.personIds = personIds;
    }
}
