package com.example.profilemanagement.controller;


import com.example.profilemanagement.dto.PersonalInfoDto;
import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class ProfileController {

    @Value("${auth.url}")
    private String authUrl;

    final
    AuthService authService;

    final
    UserService userService;

    public ProfileController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
    }


    @PutMapping("/changeInfo")
    public ResponseEntity<?> changeProfileInfo(@RequestBody PersonalInfoDto personalInfoDto,@RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);
        userService.changePersonalInfo(personalInfoDto,userDetailsDto.getEmail());

        return ResponseEntity.ok("personal info have been changed");
    }

    @GetMapping("/profile")
    public ResponseEntity<?> getProfileByID(@RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl,token);

        return ResponseEntity.ok(userService.getUser(userDetailsDto.getEmail()));

    }

}
