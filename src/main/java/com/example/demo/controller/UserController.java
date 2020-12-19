package com.example.demo.controller;

import com.example.demo.common.Common;
import com.example.demo.common.Constants;
import com.example.demo.entity.RoleEntity;
import com.example.demo.entity.UserEntity;
import com.example.demo.entity.bo.BaseMessage;
import com.example.demo.entity.bo.ResponseEntityBO;
import com.example.demo.payload.request.ChangePasswordRequest;
import com.example.demo.payload.request.RegisterRequest;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.UserRepository;
import com.example.demo.service.impl.UserService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/users")
@PreAuthorize("hasRole('ADMIN')")
public class UserController {
	@Autowired
	PasswordEncoder passwordEncoder;
	@Autowired
	UserRepository userRepository;
	@Autowired
	RoleRepository roleRepository;
	Logger logger = Logger.getLogger("UserController");
	BaseMessage response;
	long timeStamp;
	@Autowired
	private UserService userService;

	@GetMapping
	public ResponseEntity<?> findAll() {
		timeStamp = Common.getTimeStamp();

		try {
			List<UserEntity> lst = userService.findAll();
			if (Common.isNullOrEmpty(lst)) {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có người dùng nào!", timeStamp);
				logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			} else {
				//tham chiếu đến đối tượng cần trả về
				response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, lst);
				logger.info(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "findAll"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ResponseEntity<?> saveUser(@Valid @RequestBody RegisterRequest registerRequest) {
		timeStamp = Common.getTimeStamp();

		try {
			UserEntity userEntity = new UserEntity(
					registerRequest.getEmail(),
					passwordEncoder.encode(registerRequest.getPassword())
			);

			// Kiểm tra xem tên quyền có tồn tại không
			Optional<RoleEntity> roleEntity = roleRepository.findByName("ROLE_USER");
			// Kiểm tra xem tên quyền có tồn tại không
			if (!roleEntity.isPresent()) {
				// Nếu không tìm thấy thì thông báo thất bại
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không tìm thấy quyền này!", timeStamp);
				logger.error(Common.createMessageLog(registerRequest, response, Common.getUserName(), timeStamp, "register"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}

			userEntity.setRoleEntities(Collections.singleton(roleEntity.get()));
			userService.save(userEntity);

			response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Thêm thành công", timeStamp);
			logger.info(Common.createMessageLog(registerRequest, response, Common.getUserName(), timeStamp, "saveUser"));
			return ResponseEntity.status(HttpStatus.OK).body(response);
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "saveUser"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<?> findById(@Valid @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();

		try {
			Optional<UserEntity> userEntity = userService.findById(id);
			if (userEntity.isPresent()) {
				//tham chiếu đến đối tượng cần trả về
				response = new ResponseEntityBO<>(Constants.SUCCESS_RESPONSE, "Thành công", timeStamp, userEntity);
				logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findbyId"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có người dùng này!", timeStamp);
				logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findById"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "findByid"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<?> updateUser(@Valid @PathVariable(value = "id", required = true) Integer id,
	                                    @Valid @RequestBody ChangePasswordRequest changePasswordRequest) {
		timeStamp = Common.getTimeStamp();

		try {
			Optional<UserEntity> userEntity = userService.findById(id);

			if (Common.isNullOrEmpty(userEntity)) {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có người dùng này!", timeStamp);
				logger.error(Common.createMessageLog(changePasswordRequest, response, Common.getUserName(), timeStamp, "updateUser"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				return userService.changePassword(changePasswordRequest, id);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(changePasswordRequest, response, Common.getUserName(), timeStamp, "updateUser"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> deleteById(@Valid @PathVariable Integer id) {
		timeStamp = Common.getTimeStamp();

		try {
			if (userService.existsById(id)) {
				userService.deleteById(id);
				//tham chiếu đến đối tượng cần trả về
				response = new BaseMessage(Constants.SUCCESS_RESPONSE, "Xóa Thành công", timeStamp);
				logger.info(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
				return ResponseEntity.status(HttpStatus.OK).body(response);
			} else {
				//sử dựng hàm khởi tạo để giúp code ngắn gọn hơn
				response = new BaseMessage(Constants.ERROR_RESPONSE, "Không có người dùng này!", timeStamp);
				logger.error(Common.createMessageLog(null, response, Common.getUserName(), timeStamp, "deleteById"));
				return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
			}
		} catch (Exception e) {
			response = new BaseMessage(Constants.ERROR_RESPONSE, e.getMessage(), timeStamp);
			logger.error(Common.createMessageLog(id, response, Common.getUserName(), timeStamp, "deleteById"));
			return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
		}
	}
}