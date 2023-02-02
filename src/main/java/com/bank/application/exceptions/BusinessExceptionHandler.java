package com.bank.application.exceptions;

import java.sql.SQLException;
import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class BusinessExceptionHandler {

	@ExceptionHandler(BusinessException.class)
	public ResponseEntity<ExceptionResponse> handleBusinessException(BusinessException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getMessage());
		response.setStatusCode(404);
		response.setStatusType("Error");
		response.setDateTime(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
	
	@ExceptionHandler(SQLException.class)
	public ResponseEntity<ExceptionResponse> handleSQLException(SQLException ex) {
		ExceptionResponse response = new ExceptionResponse();
		response.setMessage(ex.getMessage());
		response.setStatusCode(ex.getErrorCode());
		response.setStatusType("Error");
		response.setDateTime(LocalDateTime.now());
		return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
	}
}
