package com.example.demo.entity.bo;

import lombok.AllArgsConstructor;
import lombok.Data;



//sử dụng generic để nhận các kiểu dữ liệu tùy ý
@Data
public class ResponseEntityBO<T> extends BaseMessage{
    private T result;

    public ResponseEntityBO(Long errorResponse, String s, long timeStamp) {
        super(errorResponse,s,timeStamp);
    }
    public ResponseEntityBO(Long errorResponse, String s, long timeStamp,T result) {
        super(errorResponse,s,timeStamp);
        this.result=result;
    }
}
