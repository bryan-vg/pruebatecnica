package com.example.pruebatecnica.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.pruebatecnica.dto.Pet;
import com.example.pruebatecnica.dto.PetSaveRequest;
import com.example.pruebatecnica.dto.PetSaveResponse;
import com.example.pruebatecnica.service.PetService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api")
public class PetController {

    private final PetService petService;

    public PetController(PetService petService){
        this.petService = petService;
    }

    @GetMapping("/pet/{petId}")
    public ResponseEntity<Pet> getPetById(@PathVariable Integer petId){        
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(this.petService.getPetById(petId));
    }

    @PostMapping("/pet")
    public ResponseEntity<PetSaveResponse> saveNewPet(@Valid @RequestBody PetSaveRequest petRequestBody){
        return ResponseEntity
            .status(HttpStatus.CREATED)
            .body(this.petService.saveNewPet(petRequestBody));
    }

}
