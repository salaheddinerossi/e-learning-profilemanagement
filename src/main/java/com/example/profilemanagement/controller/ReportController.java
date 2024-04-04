package com.example.profilemanagement.controller;

import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.response.ReportResponse;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.ReportService;
import com.example.profilemanagement.util.ApiResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Objects;

@RestController
@RequestMapping("/report")
public class ReportController {

    @Value("${auth.url}")
    private String authUrl;

    private final AuthService authService;
    private final ReportService reportService;

    public ReportController(AuthService authService, ReportService reportService) {
        this.authService = authService;
        this.reportService = reportService;
    }

    @PostMapping("/")
    public ResponseEntity<ApiResponse<ReportResponse>> createReport(@RequestBody ReportDto reportDto, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        ReportResponse reportResponse = reportService.createReport(reportDto, userDetailsDto.getEmail());
        return ResponseEntity.ok(new ApiResponse<>(true, "Report has been created", reportResponse));
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<?>> getReport(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }
        return ResponseEntity.ok(new ApiResponse<>(true, "Success", reportService.getReportById(id)));
    }

    @GetMapping("/{state}")
    public ResponseEntity<ApiResponse<?>> getReportsByState(@PathVariable("state") String state, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }

        switch (state.toLowerCase()) {
            case "all" -> {
                return ResponseEntity.ok(new ApiResponse<>(true, "All reports", reportService.getAllReports()));
            }
            case "archived" -> {
                return ResponseEntity.ok(new ApiResponse<>(true, "Archived reports", reportService.getArchivedReports()));
            }
            case "new" -> {
                return ResponseEntity.ok(new ApiResponse<>(true, "New reports", reportService.getNewReports()));
            }
            default -> {
                return ResponseEntity.badRequest().body(new ApiResponse<>(false, "Invalid report state", null));
            }
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<String>> archiveReport(@PathVariable Long id, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(new ApiResponse<>(false, "You are not allowed", null));
        }
        reportService.archiveReport(id);
        return ResponseEntity.ok(new ApiResponse<>(true, "Report has been archived", null));
    }

    private Boolean isAdmin(UserDetailsDto userDetailsDto) {
        return Objects.equals(userDetailsDto.getRole(), "ROLE_ADMIN");
    }
}
