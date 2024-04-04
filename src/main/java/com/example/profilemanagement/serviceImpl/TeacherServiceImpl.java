package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.TeacherDto;
import com.example.profilemanagement.exception.ResourceNotFoundException;
import com.example.profilemanagement.mapper.TeacherMapper;
import com.example.profilemanagement.model.Teacher;
import com.example.profilemanagement.repository.TeacherRepository;
import com.example.profilemanagement.service.TeacherService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;


@Service
public class TeacherServiceImpl implements TeacherService {

    final
    TeacherRepository teacherRepository;

    final
    TeacherMapper teacherMapper;

    public TeacherServiceImpl(TeacherRepository teacherRepository, TeacherMapper teacherMapper) {
        this.teacherRepository = teacherRepository;
        this.teacherMapper = teacherMapper;
    }

    @Override
    public TeacherDto getTeacher(String email) {
        return teacherMapper.teacherToTeacherDto(findTeacherByEmail(email));
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        return teacherMapper.teacherToTeacherDto(findTeacherById(id));
    }

    @Override
    public void activateAccount(Long id) {
        Teacher teacher = findTeacherById(id);
        teacher.setIsActive(true);

        teacherRepository.save(teacher);
    }

    @Override
    public void deactivateAccount(Long id) {
        Teacher teacher = findTeacherById(id);
        teacher.setIsActive(false);

        teacherRepository.save(teacher);
    }

    @Override
    public List<TeacherDto> activeAccounts() {

        return getTeachersByAccountState(true);
    }

    @Override
    public List<TeacherDto> deactivatedAccounts() {
        return getTeachersByAccountState(false);
    }

    @Override
    public List<TeacherDto> unreviewedAccounts() {
        return getTeachersByAccountState(null);
    }

    @Override
    public List<TeacherDto> allAccounts() {
        List<Teacher> teachers = teacherRepository.findAll();

        return teacherMapper.teacherListToTeacherDtoList(teachers);
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        return  teacherRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this email: "+email)
        );
    }



    public List<TeacherDto> getTeachersByAccountState(Boolean state){
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDto> teacherDtos = new ArrayList<>();

        for(Teacher teacher:teachers){
            if (teacher.getIsActive()==state){
                teacherDtos.add(teacherMapper.teacherToTeacherDto(teacher));
            }
        }

        return teacherDtos;
    }

    public  Teacher findTeacherById(Long id){
        return teacherRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this id: "+id)
        );
    }

}
