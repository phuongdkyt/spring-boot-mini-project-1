package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/account")
@PreAuthorize("isAuthenticated()")
public class AccountController {
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    private UserService userService;
    private UserPrincipal userPrincipal;

    @GetMapping({"/", ""})
    public ResponseEntity<?> accountInfo() {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (null == userPrincipal) {
            return new ResponseEntity(new MessageResponse(false, "Cannot find user info"), HttpStatus.BAD_REQUEST);
        }

        UserEntity userEntity = userService.findById(userPrincipal.getId());

        return new ResponseEntity(userEntity, HttpStatus.OK);
    }

    @PutMapping("/password")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userEntity = userEntity = userService.findById(userPrincipal.getId());

        if (!passwordEncoder.matches(changePasswordRequest.getOld_password(), userEntity.getPassword())) {
            return new ResponseEntity(new MessageResponse(false, "Old password does not match"),
                    HttpStatus.BAD_REQUEST);
        }

        if (passwordEncoder.matches(changePasswordRequest.getNew_password(), userEntity.getPassword()) || passwordEncoder.matches(changePasswordRequest.getNew_password(), changePasswordRequest.getOld_password())) {
            return new ResponseEntity(new MessageResponse(false, "New password cannot be the same as old password"),
                    HttpStatus.BAD_REQUEST);
        }

        userEntity.setPassword(passwordEncoder.encode(changePasswordRequest.getNew_password()));

        System.out.println(passwordEncoder.encode(changePasswordRequest.getNew_password()));

        userService.save(userEntity);

        return new ResponseEntity(new MessageResponse(true, "You have successfully changed your password"), HttpStatus.OK);
    }
}
