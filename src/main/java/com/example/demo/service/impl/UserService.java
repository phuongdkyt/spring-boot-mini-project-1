package com.example.demo.service.impl;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.HashMap;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    private UserPrincipal userPrincipal;

    private HashMap<String, Object> response;

    // Lấy danh sách người dùng
    public ResponseEntity<?> findAll() {
        response = new HashMap<String, Object>();

        // Trả về danh sách người dùng
        response.put("status", HttpStatus.OK.value());
        response.put("data", userRepository.findAll());
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    // Lưu thông tin người dùng
    public ResponseEntity<?> save(UserEntity userEntity) {
        response = new HashMap<String, Object>();
        userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        // Kiểm tra xem email đã tồn tại chưa
        if (userRepository.existsByEmail(userEntity.getEmail())) {
            // Nếu tồn tại thì thông báo lỗi
            response.put("status", false);
            response.put("message", "Email này đã tồn tại");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Lấy thông tin của người thêm học viên mới
        Optional<UserEntity> adminEntity = userRepository.findById(userPrincipal.getId());

        // Lấy thông tin của quyền: ROLE_USER
        Optional<RoleEntity> roleEntity = roleRepository.findByName("ROLE_USER");

        // Kiểm tra xem tên quyền có tồn tại không
        if (!roleEntity.isPresent()) {
            // Nếu không tìm thấy thì thông báo thất bại
            response.put("status", false);
            response.put("message", "Không tìm thấy quyền này");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
        }

        // Kiểm tra xem id người dùng có tồn tại không
        // Thêm thông tin của người quản lý
        adminEntity.ifPresent(userEntity::setUser);
        // Gán quyền ROLE_USER cho người dùng mới
        userEntity.setRoleEntities(Collections.singleton(roleEntity.get()));

        userRepository.save(userEntity);

        // Thông báo thêm người dùng thành công
        response.put("status", true);
        response.put("message", "Thêm người dùng mới thành công");
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, Integer id) {
        response = new HashMap<String, Object>();

        // Tìm kiếm người dùng theo id
        Optional<UserEntity> userEntity = userRepository.findById(id);

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

    public ResponseEntity<?> findById(Integer id) {
        response = new HashMap<String, Object>();

        // Tìm kiếm người dùng theo id
        Optional<UserEntity> userEntity = userRepository.findById(id);

        // Kiểm tra xem id người dùng có tồn tại không
        if (userEntity.isPresent()) {
            // Trả về thông tin người dùng theo id
            response.put("status", true);
            response.put("data", userEntity.get());
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // Ngược lại thì thông báo thất bại
        response.put("status", false);
        response.put("message", "Người dùng này không tồn tại");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        response = new HashMap<String, Object>();

        // Kiểm tra xem id người dùng có tồn tại không
        if (userRepository.existsById(id)) {
            // Nếu id người dùng tồn tại thì xoá người dùng
            userRepository.deleteById(id);

            // Thông báo xoá người dùng thành công
            response.put("status", true);
            response.put("message", "Xoá người dùng thành công");
            return ResponseEntity.status(HttpStatus.OK).body(response);
        }

        // Ngược lại thì thông báo thất bại
        response.put("status", false);
        response.put("message", "Người dùng này không tồn tại");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }

}
