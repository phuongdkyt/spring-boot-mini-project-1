package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.service.impl.AuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    Logger logger = Logger.getLogger("AuthController");
    BaseMessage response;
    long timeStamp;

    @Autowired
    private AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<?> loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        timeStamp = Common.getTimeStamp();
        try {
            //sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
            return authService.login(loginRequest);
        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(loginRequest, response, null, timeStamp, "loginUser"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        timeStamp = Common.getTimeStamp();
        try {
            return authService.register(signupRequest);
        } catch (Exception e) {
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(signupRequest, response, null, timeStamp, "loginUser"));
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }
    }

    @PutMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @GetMapping("/token")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> refreshToken() {
        return authService.refreshToken();
    }
}