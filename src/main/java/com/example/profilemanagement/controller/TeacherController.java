package com.example.profilemanagement.controller;

import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.TeacherService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/teacher")
public class TeacherController {

    @Value("${auth.url}")
    private String authUrl;

    final
    TeacherService teacherService;


    public TeacherController(TeacherService teacherService, AuthService authService) {
        this.teacherService = teacherService;
        this.authService = authService;
    }

    final
    AuthService authService;

    @GetMapping("/{id}")
    public ResponseEntity<?> getTeacherById(@PathVariable Long id){
        return ResponseEntity.ok(teacherService.getTeacherById(id));
    }

    @GetMapping("/accounts/{state}")
    public ResponseEntity<?> getAccountsByState(@PathVariable("state") String state, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        return switch (state.toLowerCase()) {
            case "all" -> ResponseEntity.ok(teacherService.allAccounts());
            case "active" -> ResponseEntity.ok(teacherService.activeAccounts());
            case "deactivated" -> ResponseEntity.ok(teacherService.deactivatedAccounts());
            case "unreviewed" -> ResponseEntity.ok(teacherService.unreviewedAccounts());
            default -> ResponseEntity.badRequest().body("Invalid account state");
        };
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<?> activateAccount(@PathVariable Long id,@RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        teacherService.activateAccount(id);
        return ResponseEntity.ok("account activated");

    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<?> deactivateAccount(@PathVariable Long id,@RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        teacherService.deactivateAccount(id);

        return ResponseEntity.ok("account deactivated");
    }

    public Boolean isAdmin(UserDetailsDto userDetailsDto){
        return Objects.equals(userDetailsDto.getRole(), "ROLE_ADMIN");
    }

}
