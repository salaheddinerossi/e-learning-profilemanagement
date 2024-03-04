package com.example.profilemanagement.model;


import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Data;


@Data
@Entity
@DiscriminatorValue("STUDENT")
public class Student extends User {

}

