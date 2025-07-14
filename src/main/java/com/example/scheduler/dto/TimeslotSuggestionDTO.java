package com.example.scheduler.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Data Transfer Object for returning suggested available time slots.
 */
public class TimeslotSuggestionDTO {

    private List<LocalDateTime> suggestedTimeslots;

    /**
     * Default constructor for deserialization.
     */
    public TimeslotSuggestionDTO() {}

    /**
     * Parameterized constructor.
     *
     * @param suggestedTimeslots the list of available time slots
     */
    public TimeslotSuggestionDTO(List<LocalDateTime> suggestedTimeslots) {
        this.suggestedTimeslots = suggestedTimeslots;
    }

    /** @return list of suggested available timeslots */
    public List<LocalDateTime> getSuggestedTimeslots() {
        return suggestedTimeslots;
    }

    public void setSuggestedTimeslots(List<LocalDateTime> suggestedTimeslots) {
        this.suggestedTimeslots = suggestedTimeslots;
    }
}
