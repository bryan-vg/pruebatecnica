package com.example.pruebatecnica.apiclients;

import java.io.IOException;

import org.springframework.stereotype.Component;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.extern.log4j.Log4j2;

@Log4j2
@Component
public class RestClient {

    public static final String APPLICATION_JSON = "application/json";
    
    private final OkHttpClient okHttpClient;

    public RestClient(OkHttpClient okHttpClient){
        this.okHttpClient = okHttpClient;
    }

    public String retrievePetById(Integer petId) throws IOException{
        if (petId == null || petId <= 0) {
            log.error("Pet Id must be a positive number");
            throw new IllegalArgumentException("Pet Id must be a positive number");
        }
        
        Request request = new Request.Builder()
            .url(
                String.format("https://petstore.swagger.io/v2/pet/%d", petId)
            )
            .get()
            .addHeader("Accept", APPLICATION_JSON)
            .build();

        try(Response response = this.okHttpClient.newCall(request).execute()){
            if(!response.isSuccessful()){
                log.info("Failed pet retrieval. Response code " + response.code());
                if(response.code() == 404){
                    log.info("Couldn't find a Pet");
                    return null;
                }
                if(response.body() == null){
                    log.info("Unexpected result from pet API call");
                    throw new IOException("Unexpected API call result");
                }
            }
            String responseBody = response.body().string();
            log.info("Successful pet retrieval: " + responseBody);
            return responseBody;
        }
    }

    public String savePet(Integer petId, String status, String name) throws IOException{
        ObjectMapper objectMapper = new ObjectMapper();
        ObjectNode objectNode = objectMapper.createObjectNode();
        
        objectNode.put("id", petId);
        objectNode.put("name", name);
        objectNode.put("status", status);

        RequestBody requestBody = RequestBody.create(
            objectNode.toString(), MediaType.get(APPLICATION_JSON));
        Request request = new Request.Builder()
            .url("https://petstore.swagger.io/v2/pet")
            .post(requestBody)
            .addHeader("Accept", APPLICATION_JSON)
            .build();
        
        try(Response response = this.okHttpClient.newCall(request).execute()){
            if(response.isSuccessful() && response.body() != null){
                String responseBody = response.body().string();
                log.info("Successfully stored a pet. Response: " + responseBody);
                return responseBody;
            }else{
                log.error("Unexpected result from save pet API call");
                throw new IOException("Unexpected save API call result");
            }
        }
    }

}
