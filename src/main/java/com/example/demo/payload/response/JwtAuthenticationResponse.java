package com.example.demo.payload.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class JwtAuthenticationResponse {
    private Integer id;
    private String email;
    private List<String> roles;
    private String accessToken;
    private String tokenType = "Bearer";
}
