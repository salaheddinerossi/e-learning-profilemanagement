package com.example.profilemanagement.service;

import com.example.profilemanagement.dto.UserDetailsDto;

public interface AuthService {

    public UserDetailsDto getUserDetailsFromAuthService(String serviceUrl, String token);

}
