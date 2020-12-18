package com.example.demo.service.impl;

import com.example.demo.entity.RoleEntity;
import com.example.demo.payload.response.MessageResponse;
import com.example.demo.repository.RoleRepository;
import com.example.demo.service.IRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class RoleService implements IRoleService {
    @Autowired
    private final RoleRepository roleRepository;

    public RoleService(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Override
    public List<RoleEntity> findAll() {
        return roleRepository.findAll();
    }

    public ResponseEntity<?> save(RoleEntity role) {

        roleRepository.save(role);

        return new ResponseEntity("OKE", HttpStatus.OK);
    }

    @Override
    public RoleEntity findById(Integer id) {
        return roleRepository.findById(id).get();
    }

    @Override
    public Optional<RoleEntity> findByName(String roleName) {
        return roleRepository.findByName(roleName);
    }

    @Override
    public ResponseEntity<?> deleteById(Integer id) {
        roleRepository.deleteById(id);

        return new ResponseEntity(new MessageResponse(true, "Xoá người dùng thành công"), HttpStatus.OK);
    }

}