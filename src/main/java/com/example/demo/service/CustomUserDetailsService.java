package com.example.demo.service;

import com.example.demo.entity.User;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    UserRepository userRepository;

    @Override
    @Transactional
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email).orElseThrow(
                () -> new UsernameNotFoundException("User not found with email :" + email)
        );

        return UserPrincipal.create(user);
    }

    @Transactional
    public UserDetails loadUserById(Integer id) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new UsernameNotFoundException("User not found with id" + id)
        );

        return UserPrincipal.create(user);
    }
}
