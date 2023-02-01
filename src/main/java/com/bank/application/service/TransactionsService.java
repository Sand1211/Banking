package com.bank.application.service;

import java.util.List;

import com.bank.application.dto.TransactionsDetailsDto;
import com.bank.application.entity.Transactions;

public interface TransactionsService {

	List<Transactions> transferAmount(TransactionsDetailsDto transactionsDetailsDto);

	String depositAmount(float amount, Integer accountNumber);

	List<Transactions> getAccountTransactionDetails(Integer accountNumber);

}
