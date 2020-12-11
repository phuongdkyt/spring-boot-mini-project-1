package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.service.impl.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/user")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserService userService;

    @GetMapping({"", "/"})
    public ResponseEntity<?> findAll() {
        return userService.findAll();
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );

        return userService.save(userEntity);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id", required = false) int id) {
        return userService.findById(id);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateUser(@PathVariable(value = "id", required = false) Integer id, @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return userService.changePassword(changePasswordRequest, id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id", required = true) int id) {
        return userService.deleteById(id);
    }
}
