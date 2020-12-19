package com.example.demo.payload.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Validated
public class ChangePasswordRequest {

	@NotNull
	@NotBlank
	@Size(min = 6, max = 32)
	private String old_password;

	@NotNull
	@NotBlank
	@Size(min = 6, max = 32)
	private String new_password;
}
