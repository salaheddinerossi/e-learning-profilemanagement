package com.example.profilemanagement.model;


import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "report")
public class Report {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String text;

    private Boolean isArchived;

    private LocalDateTime reportDate;


    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;


}
