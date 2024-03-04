package com.example.profilemanagement.service;

import com.example.profilemanagement.dto.TeacherDto;
import com.example.profilemanagement.model.Teacher;

import java.util.List;

public interface TeacherService {

    TeacherDto getTeacher(String email);

    TeacherDto getTeacherById(Long id);

    void activateAccount(Long id);

    void deactivateAccount(Long id);


    List<TeacherDto> activeAccounts();

    List<TeacherDto> deactivatedAccounts();

    List<TeacherDto> unreviewedAccounts();

    List<TeacherDto> allAccounts();

    Teacher findTeacherByEmail(String email);


}
