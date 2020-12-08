package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {


    @Autowired
    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User save(User user) {
        return userRepository.save(user);
    }

    public User findUserById(Integer id) {
        return userRepository.findById(id).get();
    }

}
