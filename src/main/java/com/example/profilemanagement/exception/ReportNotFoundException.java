package com.example.profilemanagement.exception;

public class ReportNotFoundException extends RuntimeException{
    public ReportNotFoundException(){
        super("report not fount");
    }
}
