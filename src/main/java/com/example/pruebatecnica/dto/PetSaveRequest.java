package com.example.pruebatecnica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record PetSaveRequest(
    @Positive(message = "Pet id is required")
    Integer id, 
    @NotBlank(message = "Pet status is required")
    String status, 
    @NotBlank(message = "Pet name is required")
    String name) {

}
