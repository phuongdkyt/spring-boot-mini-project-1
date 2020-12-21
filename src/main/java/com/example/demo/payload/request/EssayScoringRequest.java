package com.example.demo.payload.request;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class EssayScoringRequest {
	@NotBlank
	private Integer id;
	@Size(max = 10000)
	private Double mark;
}
