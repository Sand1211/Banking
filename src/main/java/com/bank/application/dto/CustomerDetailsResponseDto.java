package com.bank.application.dto;

import java.util.List;

import com.bank.application.entity.Accounts;
import com.bank.application.entity.Customer;

import lombok.Data;

@Data
public class CustomerDetailsResponseDto {
	private Customer customer;
	private List<Accounts> account;
	private String responseMessage;



}
