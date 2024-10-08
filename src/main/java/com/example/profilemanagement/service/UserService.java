package com.example.profilemanagement.service;

import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.response.UserResponse;

public interface UserService {

    PersonalInfoDto changePersonalInfo(PersonalInfoDto personalInfoDto,String email);

    UserResponse getUser(String email);

}
