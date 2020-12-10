package com.example.demo.controller;

import com.example.demo.entity.UserEntity;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
    @Autowired
    private IUserService userService;

    @GetMapping({"", "/"})
    public List<UserEntity> findAll() {
        return userService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@PathVariable(value = "id", required = false) int id) {
        return new ResponseEntity(new MessageResponse(true, userService.findById(id)), HttpStatus.OK);
    }

    @PostMapping({"", "/"})
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> saveUser(@RequestBody SignupRequest signupRequest) {
        UserEntity userEntity = new UserEntity(
            signupRequest.getEmail(),
            signupRequest.getPassword()
        );

        return userService.save(userEntity);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable(value = "id", required = true) int id) {
        return userService.deleteById(id);
    }
}
