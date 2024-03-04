package com.example.profilemanagement.exception;

public class TokenNotValidException extends RuntimeException{

    public TokenNotValidException(){
        super("token not valid");
    }
}