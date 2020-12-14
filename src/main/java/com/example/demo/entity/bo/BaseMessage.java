package com.example.demo.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaseMessage{
    private Boolean status; // 0 - thanh cong, 1 - that bai
    private String message; // mo ta loi
    private long timestamp;

}
