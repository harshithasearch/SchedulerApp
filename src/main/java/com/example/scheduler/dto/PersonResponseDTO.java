package com.example.scheduler.dto;

/**
 * Data Transfer Object for sending person details in API responses.
 */
public class PersonResponseDTO {

    private Long id;
    private String name;
    private String email;

    /**
     * Default constructor for deserialization.
     */
    public PersonResponseDTO() {}

    /**
     * Parameterized constructor.
     *
     * @param id    the person's ID
     * @param name  the person's name
     * @param email the person's email address
     */
    public PersonResponseDTO(Long id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    /** @return the ID of the person */
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
