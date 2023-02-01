package com.bank.application.dto;

import com.bank.application.entity.Customer;

import lombok.Data;

@Data
public class CustomerResponse {

	private String responseMessage;
	private String statusCode;
	private Customer customerResponse;

	

}
