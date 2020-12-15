package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class SignupRequest {
    @NotBlank
    @Size(max = 40)
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank
    @Size(min = 6, max = 20)
    private String password;
}
