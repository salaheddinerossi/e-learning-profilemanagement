package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.exception.EmailNotFoundException;
import com.example.profilemanagement.exception.UserNotFoundException;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.repository.UserRepository;
import com.example.profilemanagement.response.UserResponse;
import com.example.profilemanagement.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements UserService {

    final
    UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public void changePersonalInfo(PersonalInfoDto personalInfoDto,String email) {

        User user = userRepository.findByEmail(email).orElseThrow(
                EmailNotFoundException::new
        );

        user.setEmail(personalInfoDto.getEmail());
        user.setFirstName(personalInfoDto.getFirstName());
        user.setLastName(personalInfoDto.getLastName());

        userRepository.save(user);

    }

    @Override
    public UserResponse getUser(String email) {
        User user =  userRepository.findByEmail(email).orElseThrow(
                UserNotFoundException::new
        );

        return setUserResponse(user);
    }

    public UserResponse setUserResponse(User user){
        UserResponse userResponse = new UserResponse();

        userResponse.setId(user.getId());
        userResponse.setFirstName(user.getFirstName());
        userResponse.setLastName(user.getLastName());
        userResponse.setEmail(user.getEmail());

        return userResponse;
    }


}
