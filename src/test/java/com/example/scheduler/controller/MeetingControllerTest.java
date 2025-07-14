package com.example.scheduler.controller;

import com.example.scheduler.dto.MeetingDTO;
import com.example.scheduler.dto.PersonDTO;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the MeetingController REST endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class MeetingControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private Long person1Id;
    private Long person2Id;

    /**
     * Set up two distinct persons before each test to use as meeting participants.
     */
    @BeforeEach
    public void setup() throws Exception {
        person1Id = createPerson("Abc", "abc_" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
        person2Id = createPerson("Xyz", "xyz_" + UUID.randomUUID().toString().substring(0, 5) + "@example.com");
    }

    /**
     * Helper method to create a new person via API and return the generated ID.
     */
    private Long createPerson(String name, String email) throws Exception {
        PersonDTO person = new PersonDTO(name, email);

        String response = mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(person)))
                .andExpect(status().isOk())
                .andReturn().getResponse().getContentAsString();

        JsonNode jsonNode = objectMapper.readTree(response);
        return jsonNode.get("id").asLong();
    }

    /**
     * Test successful creation of a valid 1-hour meeting with two participants.
     */
    @Test
    public void testCreateValidMeeting() throws Exception {
        MeetingDTO dto = new MeetingDTO();
        dto.setTitle("Team Sync");
        dto.setStartTime(LocalDateTime.of(2025, 7, 14, 10, 0));
        dto.setPersonIds(List.of(person1Id, person2Id));

        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title", is("Team Sync")))
                .andExpect(jsonPath("$.participants", hasSize(2)));
    }

    /**
     * Test that a meeting not starting at the hour is rejected.
     */
    @Test
    public void testHalfHourMeetingError() throws Exception {
        MeetingDTO dto = new MeetingDTO();
        dto.setTitle("Short Meeting");
        dto.setStartTime(LocalDateTime.of(2025, 7, 14, 10, 30)); // Not on the hour
        dto.setPersonIds(List.of(person1Id, person2Id));

        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", is("Meeting must start at the hour mark.")));
    }

    /**
     * Test that a meeting with no participants is rejected.
     */
    @Test
    public void testCreateMeetingWithNoParticipants() throws Exception {
        MeetingDTO dto = new MeetingDTO();
        dto.setTitle("No One Here");
        dto.setStartTime(LocalDateTime.of(2025, 7, 14, 11, 0)); // Valid time
        dto.setPersonIds(List.of()); // Empty list

        mockMvc.perform(post("/api/meetings")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(dto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", is("Meeting must have at least one participant.")));
    }
}
