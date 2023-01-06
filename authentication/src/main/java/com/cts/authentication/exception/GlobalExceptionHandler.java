package com.cts.authentication.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {
	@ExceptionHandler(UserNotFoundException.class)
	public ResponseEntity<CustomErrorResponse> handleUsernameNotFoundException(
			UserNotFoundException userNotFoundException) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setMsg(userNotFoundException.getMessage());
		customErrorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.NOT_FOUND);
	}
	
	@ExceptionHandler(UserNameNumericException.class)
	public ResponseEntity<CustomErrorResponse> handleUserNameNumericException(
			UserNameNumericException userNameNumericException) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setMsg(userNameNumericException.getMessage());
		customErrorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.BAD_REQUEST);
	}
	
	@ExceptionHandler(InternalServerException.class)
	public ResponseEntity<CustomErrorResponse> handleInternalServerException(
			InternalServerException internalServerException) {
		CustomErrorResponse customErrorResponse = new CustomErrorResponse();
		customErrorResponse.setMsg(internalServerException.getMessage());
		customErrorResponse.setDateTime(LocalDateTime.now());
		return new ResponseEntity<CustomErrorResponse>(customErrorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}


}
