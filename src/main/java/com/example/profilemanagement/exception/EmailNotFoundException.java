package com.example.profilemanagement.exception;

public class EmailNotFoundException extends RuntimeException{
    public EmailNotFoundException(){
        super("email doesn't exist");
    }
}
