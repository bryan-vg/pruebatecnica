package com.example.pruebatecnica.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record PetSaveRequest(
    @NotNull(message = "Pet id is required")
    @Positive(message = "Pet id must be a positive number")
    Integer id, 
    @NotBlank(message = "Pet status is required")
    String status, 
    @NotBlank(message = "Pet name is required")
    String name) {
}
