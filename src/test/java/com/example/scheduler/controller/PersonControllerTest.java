package com.example.scheduler.controller;

import com.example.scheduler.dto.PersonDTO;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Integration tests for the PersonController REST endpoints.
 */
@SpringBootTest
@AutoConfigureMockMvc
public class PersonControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    private PersonDTO abc;
    private PersonDTO xyz;

    /**
     * Prepares unique person DTOs before each test to avoid email duplication.
     */
    @BeforeEach
    public void setup() {
        long suffix = System.nanoTime();
        abc = new PersonDTO("Abc", "abc_" + suffix + "@example.com");
        xyz = new PersonDTO("Xyz", "xyz_" + (suffix + 1) + "@example.com");
    }

    /**
     * Tests creating two persons and retrieving the list of all persons.
     */
    @Test
    public void testCreatePersonAndList() throws Exception {
        // Create first person
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(abc)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Abc")));

        // Create second person
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(xyz)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name", is("Xyz")));

        // Retrieve all persons and validate names exist
        mockMvc.perform(get("/api/persons"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].name", hasItems("Abc", "Xyz")));
    }

    /**
     * Tests that creating a person with a duplicate email fails with a proper error message.
     */
    @Test
    public void testDuplicateEmail() throws Exception {
        PersonDTO duplicate = new PersonDTO("Duplicate", "dupe_" + System.nanoTime() + "@example.com");

        // First request should succeed
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isOk());

        // Second request with the same email should fail
        mockMvc.perform(post("/api/persons")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(duplicate)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.details", containsString("Email already exists")));
    }
}
