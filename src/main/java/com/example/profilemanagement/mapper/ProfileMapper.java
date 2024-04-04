package com.example.profilemanagement.mapper;


import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    void updateUserInfoFromDto(PersonalInfoDto personalInfoDto, @MappingTarget User user);

    UserResponse userToUserResponse(User user);
}
