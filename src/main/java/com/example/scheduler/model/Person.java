package com.example.scheduler.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Entity representing a person who can participate in meetings.
 */
@Entity
public class Person {

    /** Unique identifier for the person (auto-generated). */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /** Name of the person. */
    private String name;

    /**
     * Email address of the person.
     * Must be unique and not null.
     */
    @Column(unique = true, nullable = false)
    private String email;

    /**
     * Meetings that this person is attending.
     * Inverse side of the many-to-many relationship.
     */
    @ManyToMany(mappedBy = "persons")
    private List<Meeting> meetings = new ArrayList<>();

    /**
     * Default constructor for JPA.
     */
    public Person() {}

    /**
     * Parameterized constructor for application use.
     *
     * @param id    the person's ID
     * @param name  the person's name
     * @param email the person's email address
     */
    public Person(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return the person's ID */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    /** @return the person's name */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** @return the person's email address */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    /** @return the list of meetings the person is part of */
    public List<Meeting> getMeetings() {
        return meetings;
    }

    public void setMeetings(List<Meeting> meetings) {
        this.meetings = meetings;
    }
}
