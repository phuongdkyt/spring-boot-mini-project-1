package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.SignupRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Optional;

@Service
public class AuthService implements IAuthService {
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

    private HashMap<String, Object> response;

    private UserPrincipal userPrincipal;

    @Override
    public HashMap<String, Object> login(LoginRequest loginRequest) {
        response = new HashMap<String, Object>();

        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = tokenProvider.generateToken(authentication);

        response.put("accessToken", jwt);
        response.put("tokenType", "Bearer");

        return response;
    }

    @Override
    public HashMap<String, Object> register(SignupRequest signupRequest) {
        response = new HashMap<String, Object>();
        if (userRepository.existsByEmail(signupRequest.getEmail())) {
            response.put("message", "Email is already taken");
            return response;
        }
        UserEntity userEntity = new UserEntity(
                signupRequest.getEmail(),
                passwordEncoder.encode(signupRequest.getPassword())
        );
        Optional<RoleEntity> roleEntity = roleRepository.findByName("ROLE_ADMIN");
        // Kiểm tra xem tên quyền có tồn tại không
        if (!roleEntity.isPresent()) {
            // Nếu không tìm thấy thì thông báo thất bại
            response.put("message", "Không tìm thấy quyền này");
            return response;
        }
        userRepository.save(userEntity);
        response.put("message", "Đăng ký tài khoản thành công");
        return response;
    }

    @Override
    public UserPrincipal info() {
        userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (Common.isNullOrEmpty(userPrincipal)) {
            return null;
        }
        return userPrincipal;
    }
    @Override
    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest) {
        response = new HashMap<String, Object>();
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Tìm kiếm người dùng theo id
        Optional<UserEntity> userEntity = userRepository.findById(userPrincipal.getId());

        // Kiểm tra xem id người dùng có tồn tại không
        if (!userEntity.isPresent()) {
            // Ngược lại thì thông báo thất bại
            response.put("status", false);
            response.put("message", "Người dùng này không tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Kiểm tra mật khẩu cũ không khớp
        if (!passwordEncoder.matches(changePasswordRequest.getOld_password(), userEntity.get().getPassword())) {
            response.put("status", false);
            response.put("message", "Mật khẩu cũ không khớp");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Kiểm tra mật khẩu mới trùng với mật khẩu cũ
        if (
                passwordEncoder.matches(changePasswordRequest.getNew_password(), userEntity.get().getPassword())
                        || changePasswordRequest.getNew_password().equals(changePasswordRequest.getOld_password())
        ) {
            response.put("status", false);
            response.put("message", "Mật khẩu mới không được trùng với mật khẩu cũ");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Mã hoá mật khẩu
        userEntity.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNew_password()));

        userRepository.save(userEntity.get());

        // Thông báo đổi mật khẩu thành công
        response.put("status", true);
        response.put("message", "Đổi mật khẩu mới thành công");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @Override
    public ResponseEntity<?> refreshToken() {
//        return authService.refreshToken();
        return null;
    }
}
