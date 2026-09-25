package com.example.pruebatecnica.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@ToString
public class PetSaveResponse {
    private String transactionId; 
    private String dateCreated; 
    private boolean status; 
    private String name;
}
