package com.example.profilemanagement.serviceImpl;

import com.example.profilemanagement.dto.UserDetailsDto;
import com.example.profilemanagement.exception.InvalidTokenException;
import com.example.profilemanagement.service.AuthService;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;


@Service
public class AuthServiceImpl implements AuthService {


    private final RestTemplate restTemplate;


    public AuthServiceImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public UserDetailsDto getUserDetailsFromAuthService(String serviceUrl, String token) {
        HttpHeaders headers = new HttpHeaders();
        if (!token.startsWith("Bearer ")) {
            token = "Bearer " + token;
        }
        headers.set("Authorization", token);
        HttpEntity<String> entity = new HttpEntity<>("parameters", headers);

        try{
            ResponseEntity<UserDetailsDto> response = restTemplate.exchange(
                    serviceUrl,
                    HttpMethod.GET,
                    entity,
                    UserDetailsDto.class
            );
            return response.getBody();

        }catch (HttpClientErrorException ex){
            throw new InvalidTokenException("token is not valid");
        }

    }
}
