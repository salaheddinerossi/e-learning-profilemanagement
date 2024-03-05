package com.example.profilemanagement.controller;

import com.example.profilemanagement.dto.ReportDto;
import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.service.AuthService;
import com.example.profilemanagement.service.ReportService;
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

    final
    AuthService authService;

    final ReportService reportService;

    public ReportController(AuthService authService, ReportService reportService) {
        this.authService = authService;
        this.reportService = reportService;
    }

    @PostMapping("/")
    ResponseEntity<?> createReport(@RequestBody ReportDto reportDto, @RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        reportService.createReport(reportDto,userDetailsDto.getEmail());

        return ResponseEntity.ok("report has been created");
    }

    @GetMapping("/{id}")
    ResponseEntity<?> getReport(@PathVariable Long id, @RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        return ResponseEntity.ok(reportService.getReportById(id));
    }

    @GetMapping("/accounts/{state}")
    public ResponseEntity<?> getAccountsByState(@PathVariable("state") String state, @RequestHeader("Authorization") String token) {
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        return switch (state.toLowerCase()) {
            case "all" -> ResponseEntity.ok(reportService.getAllReports());
            case "archived" -> ResponseEntity.ok(reportService.getArchivedReports());
            case "new" -> ResponseEntity.ok(reportService.getNewReports());
            default -> ResponseEntity.badRequest().body("Invalid account state");
        };
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> archiveReport(@PathVariable Long id,@RequestHeader("Authorization") String token){
        UserDetailsDto userDetailsDto = authService.getUserDetailsFromAuthService(authUrl, token);
        if (!isAdmin(userDetailsDto)) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not allowed");
        }

        reportService.archiveReport(id);

        return ResponseEntity.ok("account has been archived");
    }

    public Boolean isAdmin(UserDetailsDto userDetailsDto){
        return Objects.equals(userDetailsDto.getRole(), "ROLE_ADMIN");
    }

}
