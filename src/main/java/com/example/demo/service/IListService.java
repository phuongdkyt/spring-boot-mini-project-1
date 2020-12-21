package com.example.demo.service;

import org.springframework.http.ResponseEntity;

public interface IListService {
	public ResponseEntity<?> findAllByUserId(Integer userId);

	public ResponseEntity<?> getMarkOnTotalQuestion(Integer testId);
}
