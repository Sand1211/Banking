package com.bank.application.service;

import java.util.List;

import com.bank.application.entity.Accounts;
import com.bank.application.entity.Customer;

public interface AccountService {

	Accounts createAccount(Accounts account);

	List<Accounts> getAccountDetails();

	Accounts getAccountById(Integer accountNumber);
	
	void activateAccount(Integer accountNumber);

	String deleteAccount(Integer accountNumber);

	Customer customerExists(Integer cifNumber);
	
}
