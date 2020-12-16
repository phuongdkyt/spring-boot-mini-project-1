package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.QuestionEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.impl.AuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.xml.ws.Response;
import java.util.HashMap;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    JwtTokenProvider tokenProvider;

    @Autowired
    private AuthService authService;

    Logger logger = Logger.getLogger("AuthController");
    @PostMapping("/login")
    public BaseMessage loginUser(@Valid @RequestBody LoginRequest loginRequest) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            HashMap<String,Object> login =authService.login(loginRequest);
            if (Common.isNullOrEmpty(login)) {
                //sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
                response = new BaseMessage(Constants.ERROR_RESPONSE, "Khong the dang nhap!", timeStamp);
                logger.error(Common.createMessageLog(loginRequest, response, null, timeStamp, "loginUser"));
//                response.setErrorCode(Constants.ERROR_RESPONSE);
//                response.setMessage("Khong co cau hoi nao!"); // lay tu cau hinh
//                response.setTimestamp(timeStamp);
            } else {
                //tham chiếu đến đối tượng cần trả về
                response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, login);
                logger.info(Common.createMessageLog(loginRequest, response, null, timeStamp, "loginUser"));
//                response.setErrorCode(Constants.SUCCESS_RESPONSE);
//                response.setMessage("Thanh cong"); // lay tu cau hinh
//                response.setResult(lst);
//                response.setTimestamp(timeStamp);
            }
        }catch (Exception e){
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(loginRequest, response, null, timeStamp, "loginUser"));
        }
        return response;
//        return authService.login(loginRequest);
    }

    @PostMapping("/register")
//    @PreAuthorize("hasRole('ADMIN')")
    public BaseMessage registerUser(@Valid @RequestBody SignupRequest signupRequest) {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            HashMap<String,Object> login =authService.register(signupRequest);
            if (Common.isNullOrEmpty(signupRequest)) {
                //sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
                response = new BaseMessage(Constants.ERROR_RESPONSE, "Khong the dang nhap!", timeStamp);
                logger.error(Common.createMessageLog(signupRequest, response, null ,timeStamp, "loginUser"));
//                response.setErrorCode(Constants.ERROR_RESPONSE);
//                response.setMessage("Khong co cau hoi nao!"); // lay tu cau hinh
//                response.setTimestamp(timeStamp);
            } else {
                //tham chiếu đến đối tượng cần trả về
                response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, login);
                logger.info(Common.createMessageLog(signupRequest, response, null, timeStamp, "loginUser"));
//                response.setErrorCode(Constants.SUCCESS_RESPONSE);
//                response.setMessage("Thanh cong"); // lay tu cau hinh
//                response.setResult(lst);
//                response.setTimestamp(timeStamp);
            }
        }catch (Exception e){
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(signupRequest, response, null, timeStamp, "loginUser"));
        }
        return response;
    }

    @GetMapping("/info")
    @PreAuthorize("isAuthenticated()")
    public BaseMessage accountInfo() {
        BaseMessage response;
        long timeStamp = Common.getTimeStamp();
        try {
            UserPrincipal userPrincipal =authService.info();
            if (Common.isNullOrEmpty(userPrincipal)) {
                //sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
                response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy người dùng này!", timeStamp);
                logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "accountInfo"));
            } else {
                //tham chiếu đến đối tượng cần trả về
                response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, userPrincipal);
                logger.info(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "accountInfo"));
            }
        }catch (Exception e){
            response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
            logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "accountInfo"));
        }
        return response;
    }

    @PutMapping("/password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
        return authService.changePassword(changePasswordRequest);
    }

    @GetMapping("/token")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> refreshToken() {
        return authService.refreshToken();
    }
}