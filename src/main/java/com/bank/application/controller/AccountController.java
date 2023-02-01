package com.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.AccountCreationResponseDto;
import com.bank.application.entity.Accounts;
import com.bank.application.entity.Customer;
import com.bank.application.service.AccountService;

@RestController
@RequestMapping("/account")
public class AccountController {

	
	@Autowired
	AccountService accountService;

	@PostMapping("/create")
	public ResponseEntity<AccountCreationResponseDto> createAccount(@RequestBody Accounts account) {
		AccountCreationResponseDto accountResponse = new AccountCreationResponseDto();
		Accounts createAccount = accountService.createAccount(account);
		if (createAccount == null) {
			accountResponse.setResponseMessage("Account Creation Failed");
			accountResponse.setStatusCode("404");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountResponse);
		}
		accountResponse.setResponseMessage("Account Created Successfully");
		accountResponse.setStatusCode("200");
		return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
	}

	@GetMapping
	public List<Accounts> findAccounts() {
		List<Accounts> accountDetails = accountService.getAccountDetails();
		return accountDetails;
	}
	
	@PostMapping("/activate/{accountNumber}")
	public String activateAccount(@PathVariable(value = "accountNumber") Integer accountNumber) {
		 accountService.activateAccount(accountNumber);
		return "Account Activated";
	}

	@GetMapping("/{accountNumber}")
	public Accounts findAccountsById(@PathVariable(value = "accountNumber") Integer accountNumber) {
		Accounts accountDetails = accountService.getAccountById(accountNumber);
		return accountDetails;
	}

	@PutMapping("/update")
	@SuppressWarnings("null")
	public ResponseEntity<AccountCreationResponseDto> updateAccounts(@RequestBody Accounts accounts) {
		AccountCreationResponseDto accountResponse = null;
		Customer customerExists = accountService.customerExists(accounts.getCifNumber());
		if (customerExists == null) {
			accountResponse.setResponseMessage("CIF Number Does not Exists");
			accountResponse.setStatusCode("404");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(accountResponse);
		}
		accountService.createAccount(accounts);
		accountResponse.setResponseMessage("Account Updated Successfully");
		accountResponse.setStatusCode("404");
		return ResponseEntity.status(HttpStatus.CREATED).body(accountResponse);
	}

	@DeleteMapping("/{accountNumber}")
	public ResponseEntity<String> deleteAccount(@PathVariable(value = "accountNumber") Integer accountNumber) {
		String delete = accountService.deleteAccount(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(delete);
	}
}
