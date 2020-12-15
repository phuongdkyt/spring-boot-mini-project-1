package com.example.demo.payload.request;

import lombok.Data;

import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
public class ResultEssayRequest {
    @NotBlank
    private Integer id;
    @Size(max = 10000)
    private String resutl_essay;
}
