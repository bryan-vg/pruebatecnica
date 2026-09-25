package com.example.pruebatecnica.service.impl;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.example.pruebatecnica.apiclients.RestClient;
import com.example.pruebatecnica.dto.Pet;
import com.example.pruebatecnica.dto.PetSaveRequest;
import com.example.pruebatecnica.dto.PetSaveResponse;
import com.example.pruebatecnica.exception.RemoteApiCallException;
import com.example.pruebatecnica.service.PetService;
import com.fasterxml.jackson.databind.ObjectMapper;

@Service
public class PetServiceImpl implements PetService{

    private static final DateTimeFormatter SYSTEM_TIMEZON_FORMATTER = 
        DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss.SSSSSS");

    private final RestClient restClient;
    private final ObjectMapper objectMapper;

    public PetServiceImpl(RestClient restClient, ObjectMapper objectMapper){
        this.restClient = restClient;
        this.objectMapper = objectMapper;
    }

    @Override
    public Pet getPetById(Integer petId){
        try{
            // request https://petstore.swagger.io/#/

            // throw not found exception if api call result is null
            String petRetrievalResult = this.restClient.retrievePetById(petId);
            if(petRetrievalResult == null){
                throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No pet found");
            }
            Pet resultPet = objectMapper.readValue(petRetrievalResult, Pet.class);
            System.out.println("Parsed pet values: " + resultPet.toString());
            return resultPet;
        }catch(IOException e){
            System.out.println("Unexpected error in the external API read call");
            throw new RemoteApiCallException("Unexpected error in the external API read call", e);
        }
    }

    @Override
    public PetSaveResponse saveNewPet(PetSaveRequest petSaveRequest){
        try{
            String petSaveResult = this.restClient.savePet(petSaveRequest.id(), petSaveRequest.status(), petSaveRequest.name());
            Pet resultPet = objectMapper.readValue(petSaveResult, Pet.class);
            UUID uuid = UUID.randomUUID();
            
            PetSaveResponse petSaveResponse = new PetSaveResponse(
                UUID.randomUUID().toString(), 
                LocalDateTime.now().format(SYSTEM_TIMEZON_FORMATTER), 
                true, 
                resultPet.name());
            System.out.println("saved pet response: " + petSaveResponse.toString());
            return petSaveResponse;
        }catch(IOException e){
            System.out.println("Unexpected error in the external API save call");
            throw new RemoteApiCallException("Unexpected error in the external API save call", e);
        }
    }
}
