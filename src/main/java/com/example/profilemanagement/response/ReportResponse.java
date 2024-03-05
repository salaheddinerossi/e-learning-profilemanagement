package com.example.profilemanagement.response;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ReportResponse {

    private Long id;

    private String title;

    private String text;

    private String firstName;

    private String lastName;

    private String email;

    private LocalDateTime reportDate;

    private Boolean isArchived;


}
