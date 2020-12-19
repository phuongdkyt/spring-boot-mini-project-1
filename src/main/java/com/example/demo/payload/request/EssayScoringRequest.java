package com.example.demo.payload.request;

import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@Validated
public class EssayScoringRequest {
	@NotBlank
	private Integer id;
	@Size(max = 10000)
	private Double mark;
}
