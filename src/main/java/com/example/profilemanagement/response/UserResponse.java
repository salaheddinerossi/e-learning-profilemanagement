package com.example.profilemanagement.response;


import lombok.Data;

@Data
public class UserResponse {
    private Long id;

    private String firstName;

    private String lastName;

    private String email;

}
