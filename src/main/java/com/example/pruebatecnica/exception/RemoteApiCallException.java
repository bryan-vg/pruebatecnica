package com.example.pruebatecnica.exception;

public class RemoteApiCallException extends RuntimeException{
    public RemoteApiCallException(String message){
        super(message);
    }
    public RemoteApiCallException(String message, Throwable error){
        super(message, error);
    }
}
