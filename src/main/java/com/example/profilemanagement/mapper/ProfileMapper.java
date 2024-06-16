package com.example.profilemanagement.mapper;


import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.model.User;
import com.example.profilemanagement.response.UserResponse;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface ProfileMapper {

    @Mapping(target = "email", ignore = true)
    void updateUserInfoFromDto(PersonalInfoDto personalInfoDto, @MappingTarget User user);

    UserResponse userToUserResponse(User user);
}
