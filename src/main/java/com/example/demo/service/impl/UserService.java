package com.example.demo.service.impl;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.security.UserPrincipal;
import com.example.demo.service.IUserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {
	@Autowired
	PasswordEncoder passwordEncoder;
	Logger logger = Logger.getLogger("UserService");
	BaseMessage response;
	long timeStamp;
	@Autowired
	private UserRepository userRepository;
	@Autowired
	private RoleRepository roleRepository;
	private UserPrincipal userPrincipal;

	// Lấy danh sách người dùng
	public List<UserEntity> findAll() {
		return userRepository.findAll();
	}

	@Override
	public void save(UserEntity userEntity) {
		userRepository.save(userEntity);
	}

	@Override
	public List<UserEntity> findByEmail(String email) {
		return userRepository.findByEmailStartsWith(email);
	}

	@Override
	public boolean existsByEmail(String email) {
		return userRepository.existsByEmail(email);
	}

	@Override
	public boolean existsById(Integer id) {
		return userRepository.existsById(id);
	}

	public ResponseEntity<?> changePassword(ChangePasswordRequest changePasswordRequest, Integer id) {
		timeStamp = Common.getTimeStamp();

		// Tìm kiếm người dùng theo id
		Optional<UserEntity> userEntity = userRepository.findById(id);

		// Kiểm tra xem id người dùng có tồn tại không
		if (Common.isNullOrEmpty(userEntity)) {
			// Ngược lại thì thông báo thất bại
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Người dùng này không tồn tại", timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "changePassword"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// Kiểm tra mật khẩu cũ không khớp
		if (!passwordEncoder.matches(changePasswordRequest.getOld_password(), userEntity.get().getPassword())) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Mật khẩu cũ không khớp", timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "changePassword"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// Kiểm tra mật khẩu mới trùng với mật khẩu cũ
		if (
				passwordEncoder.matches(changePasswordRequest.getNew_password(), userEntity.get().getPassword())
						|| changePasswordRequest.getNew_password().equals(changePasswordRequest.getOld_password())
		) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, "Mật khẩu mới không được trùng với mật khẩu cũ", timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "changePassword"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}

		// Mã hoá mật khẩu
		userEntity.get().setPassword(passwordEncoder.encode(changePasswordRequest.getNew_password()));

		userRepository.save(userEntity.get());

		// Thông báo đổi mật khẩu thành công
		response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Đổi mật khẩu mới thành công", timeStamp);
		logger.info(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "changePassword"));
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}


	@Override
	public Optional<UserEntity> findById(Integer id) {
		return userRepository.findById(id);
	}

	@Override
	public void deleteById(Integer id) {
		userRepository.deleteById(id);
	}

}