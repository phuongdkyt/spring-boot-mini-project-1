package com.example.demo.payload.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@Validated
public class RegisterRequest {
	@NotBlank
	@Size(max = 40)
	@Email(message = "Email should be valid")
	private String email;

	@NotBlank
	@NotNull
	@Size(min = 6, max = 32)
	private String password;
}
