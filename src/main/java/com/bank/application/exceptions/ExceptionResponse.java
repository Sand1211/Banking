package com.bank.application.exceptions;

import java.time.LocalDateTime;

import lombok.Data;

@Data
public class ExceptionResponse {

	private String message;
	private int statusCode;
	private String statusType;
	private LocalDateTime dateTime;

	

}
