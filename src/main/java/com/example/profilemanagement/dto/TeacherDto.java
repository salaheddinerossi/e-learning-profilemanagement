package com.example.profilemanagement.dto;


import lombok.Data;

@Data
public class TeacherDto {

    private Long id;

    private String firstName;

    private String LastName;

    private String email;


    private String phoneNumber;

    private Boolean isActive;
}
