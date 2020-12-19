package com.example.demo.common;

import com.example.demo.security.UserPrincipal;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.springframework.security.core.context.SecurityContextHolder;

import java.util.List;
import java.util.Map;

public class Common {

	public static Long getTimeStamp() {
		return System.currentTimeMillis();
	}

	public static String getUserName() {
		UserPrincipal userPrincipal = (UserPrincipal) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
		return userPrincipal.getEmail();
	}

	public static boolean isNullOrEmpty(Object obj) {

		if (obj == null) {
			return true;
		}
		if (obj instanceof String) {
			return obj.toString().isEmpty();
		}
		if (obj instanceof List) {
			return ((List) obj).isEmpty();
		}
		if (obj instanceof Map) {
			return ((Map) obj).isEmpty();
		}
		return false;
	}

	public static String createMessageLog(Object input, Object response, String userName, Long timeStamp, String methodName) {
		StringBuilder sb = new StringBuilder();
		Gson gson = new Gson();
		ObjectMapper objectMapper = new ObjectMapper();
		sb.append(timeStamp + "");
		if (!isNullOrEmpty(userName)) {
			sb.append("_" + userName);
		}
		try {
			if (!isNullOrEmpty(input))
				sb.append("_" + objectMapper.writeValueAsString(input));
			if (!isNullOrEmpty(methodName))
				sb.append("_" + gson.toJson(methodName));
			if (!isNullOrEmpty(response)) {

				sb.append("_" + objectMapper.writeValueAsString(response));
			}
		} catch (Exception e) {
			e.getMessage();
		}

//    if (!isNullOrEmpty(response)) {
//        sb.append("_" + gson.toJson(response));
//    }

		return sb.toString();
	}
}
