package com.example.pruebatecnica.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public record Pet(Integer id, String name, String status) {
    public Pet{
        if(id == null || id < 1){
            throw new IllegalArgumentException("Invalid pet id");
        }
        if(name == null || name.isBlank()){
            throw new IllegalArgumentException("Invalid pet name");
        }
        if(status == null || status.isBlank()){
            throw new IllegalArgumentException("Invalid pet status");
        }
    }
}
