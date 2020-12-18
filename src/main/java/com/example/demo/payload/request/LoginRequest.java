package com.example.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class LoginRequest {

    @NotNull
    @NotBlank
    @Size(min = 6, max = 32)
    @Email
    private String email;

    @NotBlank
    @NotNull
    @Size(min = 6, max = 32)
    private String password;
}
