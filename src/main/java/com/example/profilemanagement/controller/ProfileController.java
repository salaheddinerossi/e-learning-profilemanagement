package com.example.profilemanagement.controller;

import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.response.UserResponse;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.UserService;
import com.example.profilemanagement.util.ApiResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Value("${auth.url}")
    private String authUrl;

    private final AuthService authService;
    private final UserService userService;

    @Autowired
    public ProfileController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }

    @PutMapping("/changeInfo")
    public ResponseEntity<ApiResponse<PersonalInfoDto>> changeProfileInfo(@RequestBody PersonalInfoDto personalInfoDto, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        PersonalInfoDto personalInfoDto1 =userService.changePersonalInfo(personalInfoDto, userDetailsDto.getEmail());

        ApiResponse<PersonalInfoDto> response = new ApiResponse<>(true, "Personal info has been changed", personalInfoDto1);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<UserResponse>> getProfileByID(@RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        UserResponse user = userService.getUser(userDetailsDto.getEmail());

        ApiResponse<UserResponse> response = new ApiResponse<>(true, "Success", user);
        return ResponseEntity.ok(response);
    }
}
