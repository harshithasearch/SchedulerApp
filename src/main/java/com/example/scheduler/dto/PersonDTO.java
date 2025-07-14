package com.example.scheduler.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

/**
 * Data Transfer Object for receiving person creation input.
 */
public class PersonDTO {

    @NotBlank(message = "Name must not be blank")
    private String name;

    @NotBlank(message = "Email must not be blank")
    @Email(message = "Email should be valid")
    private String email;

    /**
     * Default constructor for deserialization.
     */
    public PersonDTO() {}

    /**
     * Parameterized constructor.
     *
     * @param name  the name of the person
     * @param email the email address of the person
     */
    public PersonDTO(String name, String email) {
        this.name = name;
        this.email = email;
    }

    /** @return the name of the person */
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /** @return the email of the person */
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
