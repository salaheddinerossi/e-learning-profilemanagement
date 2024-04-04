package com.example.profilemanagement.mapper;


import com.example.profilemanagement.dto.TeacherDto;
import com.example.profilemanagement.model.Teacher;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TeacherMapper {

    TeacherDto teacherToTeacherDto(Teacher teacher);

    List<TeacherDto> teacherListToTeacherDtoList(List<Teacher> teachers);

}
