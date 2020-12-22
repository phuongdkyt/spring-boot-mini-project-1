package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.LoginRequest;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.JwtTokenProvider;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IAuthService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import javax.validation.Valid;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

	Logger logger = Logger.getLogger("AuthService");
	BaseMessage response;
	long timeStamp;
	HashMap<String, Object> result;
	UserPrincipal userPrincipal;

	@Override
	public ResponseEntity<?> login(LoginRequest loginRequest) {
		timeStamp = Common.getTimeStamp();

		try {
			if (!userRepository.existsByEmail(loginRequest.getEmail())) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Email không tồn tại, vui lòng thử lại!", timeStamp);
				logger.error(Common.createMessageLog(loginRequest, response, null, timeStamp, "login"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

			Authentication authentication = authenticationManager.authenticate(
					new UsernamePasswordAuthenticationToken(
							loginRequest.getEmail().trim(),
							loginRequest.getPassword().trim()
					)
			);
			SecurityContextHolder.getContext().setAuthentication(authentication);
			String jwt = tokenProvider.generateToken(authentication);
			userPrincipal = (UserPrincipal) authentication.getPrincipal();
			List<String> roles = userPrincipal.getAuthorities().stream().map(GrantedAuthority::getAuthority)
					.collect(Collectors.toList());

			result = new HashMap<String, Object>();
			result.put("id", userPrincipal.getId());
			result.put("email", userPrincipal.getEmail());
			result.put("roles", roles);
			result.put("accessToken", jwt);
			result.put("tokenType", "Bearer");

			response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, result);
			logger.info(Common.createMessageLog(loginRequest, response, null, timeStamp, "login"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (AuthenticationException e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Sai mật khẩu nhé !", timeStamp);
			logger.error(Common.createMessageLog(loginRequest, response, null, timeStamp, "login"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<?> register(RegisterRequest registerRequest) {
		timeStamp = Common.getTimeStamp();

		try {
			if (userRepository.existsByEmail(registerRequest.getEmail())) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Email đã tồn tại, vui lòng thử lại!", timeStamp);
				logger.error(Common.createMessageLog(registerRequest, response, null, timeStamp, "register"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			UserEntity userEntity = new UserEntity(
					registerRequest.getEmail(),
					passwordEncoder.encode(registerRequest.getPassword())
			);

			// Kiểm tra xem tên quyền có tồn tại không
			Optional<RoleEntity> roleEntity = roleRepository.findByName("ROLE_ADMIN");
			// Kiểm tra xem tên quyền có tồn tại không
			if (!roleEntity.isPresent()) {
				// Nếu không tìm thấy thì thông báo thất bại
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy quyền này!", timeStamp);
				logger.error(Common.createMessageLog(registerRequest, response, null, timeStamp, "register"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			userEntity.setRoleEntities(Collections.singleton(roleEntity.get()));
			userRepository.save(userEntity);

			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Thêm người dùng thành công!", timeStamp);
			logger.error(Common.createMessageLog(registerRequest, response, null, timeStamp, "register"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(registerRequest, response, null, timeStamp, "register"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<?> changePassword(@Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		try {
			userPrincipal =
					(UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();

			// Tìm kiếm người dùng theo id
			Optional<UserEntity> userEntity = userRepository.findById(userPrincipal.getId());

			// Kiểm tra xem id người dùng có tồn tại không
			if (!userEntity.isPresent()) {
				// Ngược lại thì thông báo thất bại
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Người dùng này không tồn tại", timeStamp);
				logger.error(Common.createMessageLog(changePasswordRequest, response, Common.getUserName(), timeStamp,
						"changePassword"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

			// Kiểm tra mật khẩu cũ không khớp
			if (!passwordEncoder.matches(changePasswordRequest.getOld_password(), userEntity.get().getPassword())) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Mật khẩu cũ không khớp", timeStamp);
				logger.error(Common.createMessageLog(changePasswordRequest, response, userPrincipal.getEmail(), timeStamp, "changePassword"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

			// Kiểm tra mật khẩu mới trùng với mật khẩu cũ
			if (
					passwordEncoder.matches(changePasswordRequest.getNew_password(), userEntity.get().getPassword())
							|| changePasswordRequest.getNew_password().equals(changePasswordRequest.getOld_password())
			) {
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Mật khẩu mới không được trùng với mật khẩu cũ", timeStamp);
				logger.error(Common.createMessageLog(changePasswordRequest, response, userPrincipal.getEmail(), timeStamp, "changePassword"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}

			// Mã hoá mật khẩu
			userEntity.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNew_password()));

			userRepository.save(userEntity.get());

			// Thông báo đổi mật khẩu thành công
			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Đổi mật khẩu mới thành công", timeStamp);
			logger.error(Common.createMessageLog(changePasswordRequest, response, userPrincipal.getEmail(), timeStamp, "changePassword"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(changePasswordRequest, response, userPrincipal.getEmail(), timeStamp, "changePassword"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@Override
	public ResponseEntity<?> refreshToken() {
//        return authService.refreshToken();
		return null;
	}
}