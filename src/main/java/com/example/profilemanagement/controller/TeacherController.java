package com.example.profilemanagement.controller;

import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.TeacherService;
import com.example.profilemanagement.util.ApiResponse;
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

    private final TeacherService teacherService;
    private final AuthService authService;

    public TeacherController(TeacherService teacherService, AuthService authService) {
        this.teacherService = teacherService;
        this.authService = authService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> getTeacherById(@PathVariable Long id) {
        Object teacher = teacherService.getTeacherById(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Teacher fetched successfully", teacher));
    }

    @GetMapping("/accounts/{state}")
    public ResponseEntity<ApiResponse<Object>> getAccountsByState(@PathVariable("state") String state, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }

        Object accountsResponse = switch (state.toLowerCase()) {
            case "all" -> teacherService.allAccounts();
            case "active" -> teacherService.activeAccounts();
            case "deactivated" -> teacherService.deactivatedAccounts();
            case "unreviewed" -> teacherService.unreviewedAccounts();
            default -> throw new IllegalStateException("Unexpected value: " + state.toLowerCase());
        };

        return ResponseEntity.ok(new ApiResponse<>(true, "Accounts fetched successfully", accountsResponse));
    }

    @PutMapping("/activate/{id}")
    public ResponseEntity<ApiResponse<String>> activateAccount(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }

        teacherService.activateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account activated", null));
    }

    @PutMapping("/deactivate/{id}")
    public ResponseEntity<ApiResponse<String>> deactivateAccount(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }

        teacherService.deactivateAccount(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Account deactivated", null));
    }

    private Boolean isAdmin(UserDetailsDto userDetailsDto) {
        return Objects.equals(userDetailsDto.getRole(), "ROLE_ADMIN");
    }
}
