package com.bank.application.dto;

import com.bank.application.constants.AccountType;

import lombok.Data;
@Data
public class AccountCreationDto {

	private float balanceAmount;
	private AccountType accountType;
	private Integer cifNumber;

	

}
