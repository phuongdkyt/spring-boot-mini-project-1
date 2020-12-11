package com.example.demo.service;

import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import org.springframework.http.ResponseEntity;

public interface IAuthService {
    public ResponseEntity<?> login(LoginRequest loginRequest);

    public ResponseEntity<?> register(SignupRequest signupRequest);

    public ResponseEntity<?> info();

    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest);

    public ResponseEntity<?> refreshToken();
}
