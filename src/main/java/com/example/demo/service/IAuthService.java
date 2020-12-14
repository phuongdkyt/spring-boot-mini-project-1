package com.example.demo.service;

import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.security.UserPrincipal;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;

public interface IAuthService {
    public HashMap<String, Object> login(LoginRequest loginRequest);

    public HashMap<String, Object> register(SignupRequest signupRequest);

    public UserPrincipal info();

    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest);

    public ResponseEntity<?> refreshToken();
}
