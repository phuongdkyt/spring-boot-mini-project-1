package com.example.demo.payload.response;

import lombok.Data;

import java.util.List;

@Data
public class JwtAuthenticationResponse {
    private String accessToken;
    private String tokenType = "Bearer";
    private List<String> roles;

    public JwtAuthenticationResponse(String accessToken, String tokenType, List<String> roles) {
        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.roles = roles;
    }

    public JwtAuthenticationResponse() {
    }
}
