package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.exception.ResourceNotFoundException;
import com.example.profilemanagement.mapper.ProfileMapper;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.repository.UserRepository;
import com.example.profilemanagement.response.UserResponse;
import com.example.profilemanagement.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final
    ProfileMapper profileMapper;

    final
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository, ProfileMapper profileMapper) {
        this.userRepository = userRepository;
        this.profileMapper = profileMapper;
    }

    @Override
    public PersonalInfoDto changePersonalInfo(PersonalInfoDto personalInfoDto,String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this email: "+ email)
        );

        profileMapper.updateUserInfoFromDto(personalInfoDto, user);

        userRepository.save(user);
        return personalInfoDto;

    }

    @Override
    public UserResponse getUser(String email) {
        User user =  userRepository.findByEmail(email).orElseThrow(
                () -> new ResourceNotFoundException("user not found with this email: "+email)
        );

        return profileMapper.userToUserResponse(user);
    }


}
