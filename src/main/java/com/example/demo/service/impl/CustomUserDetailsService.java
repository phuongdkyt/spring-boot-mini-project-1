package com.example.demo.service.impl;

import com.example.demo.entity.UserEntity;
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
		UserEntity user = userRepository.findByEmail(email).orElseThrow(
				() -> new UsernameNotFoundException("User not found with email :" + email)
		);

		return UserPrincipal.create(user);
	}

	@Transactional
	public UserDetails loadUserById(Integer id) {
		UserEntity user = userRepository.findById(id).orElseThrow(
				() -> new UsernameNotFoundException("User not found with id" + id)
		);

		return UserPrincipal.create(user);
	}
}
