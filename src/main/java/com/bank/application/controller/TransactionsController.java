package com.bank.application.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.TransactionsDetailsDto;
import com.bank.application.entity.Transactions;
import com.bank.application.service.TransactionsService;

@RestController
@RequestMapping("/transaction")
public class TransactionsController {

	@Autowired
	TransactionsService transactionsService;

	@PostMapping("/transfer")
	public ResponseEntity<List<Transactions>> transferAmount(@RequestBody TransactionsDetailsDto transactionsDetailsDto) {
		 List<Transactions> transferAmount = transactionsService.transferAmount(transactionsDetailsDto);
		return ResponseEntity.status(HttpStatus.OK).body(transferAmount);
	}

	@PostMapping("/deposit/{amount}/{accountNumber}")
	public ResponseEntity<String> depositAmount(@PathVariable("amount") float amount,
			@PathVariable("accountNumber") Integer accountNumber) {
		String response = transactionsService.depositAmount(amount, accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}

	@GetMapping("/balance/{accountNumber}")
	public ResponseEntity<List<Transactions>> getAccountTransactionDetails(
			@PathVariable("accountNumber") Integer accountNumber) {
		List<Transactions> response = transactionsService.getAccountTransactionDetails(accountNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
}
