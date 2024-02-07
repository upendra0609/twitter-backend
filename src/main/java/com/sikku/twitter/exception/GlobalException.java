package com.sikku.twitter.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.sikku.twitter.model.ExceptionDetail;

@RestControllerAdvice
public class GlobalException {

	@ExceptionHandler(UserException.class)
	public ResponseEntity<?> userException(UserException e) {
		ExceptionDetail exceptionDetail = new ExceptionDetail();
		exceptionDetail.setMessage(e.getMessage());
		exceptionDetail.setStatusCode("something went wrong");
		exceptionDetail.setDateTime(LocalDateTime.now());
		return new ResponseEntity<>(exceptionDetail,HttpStatus.EXPECTATION_FAILED);
	}

}
