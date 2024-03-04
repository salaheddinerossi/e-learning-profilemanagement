package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.TeacherDto;
import com.example.profilemanagement.exception.UserNotFoundException;
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

    public TeacherServiceImpl(TeacherRepository teacherRepository) {
        this.teacherRepository = teacherRepository;
    }

    @Override
    public TeacherDto getTeacher(String email) {
        return setTeacherDto(findTeacherByEmail(email));
    }

    @Override
    public TeacherDto getTeacherById(Long id) {
        return setTeacherDto(findTeacherById(id));
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
        List<TeacherDto> teacherDtos = new ArrayList<>();

        for (Teacher teacher : teachers ){
            teacherDtos.add(setTeacherDto(teacher));
        }

        return teacherDtos;
    }

    @Override
    public Teacher findTeacherByEmail(String email) {
        return  teacherRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );
    }

    public  TeacherDto setTeacherDto(Teacher teacher){
        TeacherDto teacherDto = new TeacherDto();

        teacherDto.setId(teacher.getId());
        teacherDto.setEmail(teacher.getEmail());
        teacherDto.setFirstName(teacher.getFirstName());
        teacherDto.setLastName(teacher.getLastName());
        teacherDto.setPhoneNumber(teacher.getPhoneNumber());
        teacherDto.setIsActive(teacher.getIsActive());

        return teacherDto;

    }

    public List<TeacherDto> getTeachersByAccountState(Boolean state){
        List<Teacher> teachers = teacherRepository.findAll();
        List<TeacherDto> teacherDtos = new ArrayList<>();

        for(Teacher teacher:teachers){
            if (teacher.getIsActive()==state){
                teacherDtos.add(setTeacherDto(teacher));
            }
        }

        return teacherDtos;
    }

    public  Teacher findTeacherById(Long id){
        return teacherRepository.findById(id).orElseThrow(
                UserNotFoundException::new
        );
    }

}
