package com.example.pruebatecnica.service;

import com.example.pruebatecnica.dto.Pet;
import com.example.pruebatecnica.dto.PetSaveRequest;
import com.example.pruebatecnica.dto.PetSaveResponse;

public interface PetService {
    public Pet getPetById(Integer petId);
    public PetSaveResponse saveNewPet(PetSaveRequest petSaveRequest);
}
