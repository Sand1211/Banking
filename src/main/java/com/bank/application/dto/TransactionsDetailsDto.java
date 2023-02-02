package com.bank.application.dto;

import lombok.Data;

@Data
public class TransactionsDetailsDto {

	private Integer toAccountNumber;
	private Integer fromAccountNumber;
	private float amount;

	

}
