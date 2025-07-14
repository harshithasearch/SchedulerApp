package com.example.scheduler.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a meeting with a title, time window, and participants.
 */
@Entity
public class Meeting {

    /** Unique identifier for the meeting (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** The title or subject of the meeting. */
    private String title;

    /** Start time of the meeting. */
    private LocalDateTime startTime;

    /** End time of the meeting. */
    private LocalDateTime endTime;

    /**
     * List of persons participating in the meeting.
     * Many-to-Many relationship is handled via a join table.
     */
    @ManyToMany
    @JoinTable(
        name = "meeting_person",
        joinColumns = @JoinColumn(name = "meeting_id"),
        inverseJoinColumns = @JoinColumn(name = "person_id")
    )
    private List<Person> persons = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public Meeting() {}

    /**
     * Full constructor for use in application logic or testing.
     *
     * @param id        the meeting ID
     * @param title     the meeting title
     * @param startTime the start time of the meeting
     * @param endTime   the end time of the meeting
     * @param persons   the participants
     */
    public Meeting(Long id, String title, LocalDateTime startTime, LocalDateTime endTime, List<Person> persons) {
        this.id = id;
        this.title = title;
        this.startTime = startTime;
        this.endTime = endTime;
        this.persons = persons;
    }

    /** @return the meeting ID */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** @return the meeting title */
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    /** @return the meeting start time */
    public LocalDateTime getStartTime() {
        return startTime;
    }

    public void setStartTime(LocalDateTime startTime) {
        this.startTime = startTime;
    }

    /** @return the meeting end time */
    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    /** @return the list of meeting participants */
    public List<Person> getPersons() {
        return persons;
    }

    public void setPersons(List<Person> persons) {
        this.persons = persons;
    }
}
