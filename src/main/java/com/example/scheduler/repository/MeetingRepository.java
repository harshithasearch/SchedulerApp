package com.example.scheduler.repository;

import com.example.scheduler.model.Meeting;
import com.example.scheduler.model.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDateTime;
import java.util.List;

/**
 * Repository interface for performing CRUD and custom queries on Meeting entities.
 */
public interface MeetingRepository extends JpaRepository<Meeting, Long> {

    /**
     * Retrieves all meetings that a specific person is participating in.
     *
     * @param personId the ID of the person
     * @return list of meetings involving the person
     */
    List<Meeting> findByPersonsId(Long personId);

    /**
     * Retrieves meetings for the given list of persons occurring between start and end times.
     * Useful for detecting overlapping meetings.
     *
     * @param persons list of persons
     * @param start   start of time range
     * @param end     end of time range
     * @return list of overlapping meetings
     */
    List<Meeting> findByPersonsInAndStartTimeBetween(List<Person> persons, LocalDateTime start, LocalDateTime end);

    /**
     * Finds all meetings that involve any of the specified person IDs.
     *
     * @param personIds list of person IDs
     * @return list of meetings involving any of those persons
     */
    List<Meeting> findByPersonsIdIn(List<Long> personIds);

    /**
     * Checks whether a meeting already exists for a given group of persons at a specified start time.
     *
     * @param persons   list of persons
     * @param startTime the start time to check
     * @return true if a conflicting meeting exists; false otherwise
     */
    boolean existsByPersonsInAndStartTime(List<Person> persons, LocalDateTime startTime);
}
