package com.example.demo.service.impl;

import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.exception.AppException;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    private UserPrincipal userPrincipal;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public List<UserEntity> findAll() {
        return userRepository.findAll();
    }

    public ResponseEntity<?> save(UserEntity user) {
        userPrincipal =
                (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        UserEntity userAdmin = userRepository.findById(userPrincipal.getId()).get();

        RoleEntity userRoleEntity = roleRepository.findByName("ROLE_USER")
                .orElseThrow(() -> new AppException("User Role not set"));
        user.setRoleEntities(Collections.singleton(userRoleEntity));

        user.setUser(userAdmin);

        userRepository.save(user);

        return new ResponseEntity(new MessageResponse(true, "Thêm người dùng mới thành công"), HttpStatus.OK);
    }

    public UserEntity findById(Integer id) {
        Optional<UserEntity> userEntity = userRepository.findById(id);

        if (userEntity.isPresent()) {
            return userEntity.get();
        }

        return null;
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        userRepository.deleteById(id);

        return new ResponseEntity(new MessageResponse(true, "Xoá người dùng thành công"), HttpStatus.OK);
    }

}
