package com.bank.application.serviceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.constants.TransactionType;
import com.bank.application.dto.TransactionsDetailsDto;
import com.bank.application.entity.Accounts;
import com.bank.application.entity.Transactions;
import com.bank.application.exceptions.BusinessException;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.TransactionsRepository;
import com.bank.application.service.TransactionsService;

@Transactional
@Service
//@Profile(value = {"dev","local","qa","prod"})
public class TransactionsServiceImpl implements TransactionsService {

	@Autowired
	TransactionsRepository transactionsRepository;

	@Autowired
	AccountRepository accountRepository;

	@Override
	public List<Transactions> transferAmount(TransactionsDetailsDto transactionsDetailsDto) {
		int fromAccount = transactionsDetailsDto.getFromAccountNumber();
		int toAccount = transactionsDetailsDto.getToAccountNumber();

		Optional<Accounts> fromAccountResponse = accountRepository.findById(fromAccount);
		boolean fromAccountResult = validateFromAccountResponse(fromAccountResponse,
				transactionsDetailsDto.getAmount());
		if (!fromAccountResult) {
			throw new BusinessException("Insufficient Balance to Transfer");
		}
		Optional<Accounts> toAccountResponse = accountRepository.findById(toAccount);
		validationForToAccount(toAccountResponse);

		float finalAmountAfterDeducted = fromAccountResponse.get().getBalanceAmount()
				- transactionsDetailsDto.getAmount();

		accountRepository.updateAmount(finalAmountAfterDeducted, fromAccount);
		float finalAmountAfterAdded = toAccountResponse.get().getBalanceAmount() + transactionsDetailsDto.getAmount();
		accountRepository.updateAmount(finalAmountAfterAdded, toAccount);
		List<Transactions> transactions = new ArrayList<>();
		Transactions deductedAccountTransaction = setTransactionDetailsForDebitAccount(fromAccount, toAccount,
				transactionsDetailsDto.getAmount(), finalAmountAfterDeducted);
		Transactions creditedAccountTransaction = setTransactionDetailsForCreditedAccount(fromAccount, toAccount,
				transactionsDetailsDto.getAmount(), finalAmountAfterAdded);
		transactions.add(deductedAccountTransaction);
		transactions.add(creditedAccountTransaction);
		List<Transactions> saveAll = transactionsRepository.saveAll(transactions);
		return saveAll;
	}

	private Transactions setTransactionDetailsForCreditedAccount(int fromAccount, int toAccount, float amount,
			float finalAmountAfterAdded) {
		Transactions transaction = new Transactions();
		transaction.setAccountNumber(toAccount);
		transaction.setAmount(amount);
		transaction.setDescription("Amount has been Credited from " + fromAccount);
		transaction.setTotalAmount(finalAmountAfterAdded);
		transaction.setTransactionType(TransactionType.CREDITED);
		return transaction;
	}

	private Transactions setTransactionDetailsForDebitAccount(int fromAccount, int toAccount, float amount,
			float finalAmountAfterDeducted) {

		Transactions transaction = new Transactions();
		transaction.setAccountNumber(fromAccount);
		transaction.setAmount(amount);
		transaction.setDescription("Amount has been debited to " + toAccount);
		transaction.setTotalAmount(finalAmountAfterDeducted);
		transaction.setTransactionType(TransactionType.DEBITED);
		return transaction;
	}

	private void validationForToAccount(Optional<Accounts> toAccountResponse) {
		if (!toAccountResponse.isPresent()) {
			throw new RuntimeException("To account doesnot exists");
		}

		if (toAccountResponse.get().getAccountStatus().equals("INACTIVE")) {
			throw new BusinessException("To account is Inactive. Cant transfer amount");
		}

	}

	private boolean validateFromAccountResponse(Optional<Accounts> fromAccountResponse, float amount) {
		if (!fromAccountResponse.isPresent()) {
			throw new RuntimeException("From account doesnot exists");
		}

		if (amount <= 0) {
			throw new RuntimeException("Please enter amount greater than 0 for transaction");

		}

		boolean result = (fromAccountResponse.get().getBalanceAmount() > amount) ? Boolean.TRUE : Boolean.FALSE;
		return result;
	}

	@Override
	public String depositAmount(float amount, Integer accountNumber) {
		Optional<Accounts> response = accountRepository.findById(accountNumber);
		if (!response.isPresent()) {
			throw new BusinessException(" Account doesnot exists " + accountNumber);
		} else if (response.get().getAccountStatus().equals("INACTIVE")) {
			throw new BusinessException("Account is not active so can not send amount");
		} else {
			float updatedAmount = response.get().getBalanceAmount() + amount;
			response.get().setBalanceAmount(updatedAmount);
			accountRepository.save(response.get());
			Transactions transaction = new Transactions();
			transaction.setAccountNumber(accountNumber);
			transaction.setAmount(amount);
			transaction.setDescription("Amount has been deposited");
			transaction.setTotalAmount(updatedAmount);
			transaction.setTransactionType(TransactionType.CREDITED);
			transactionsRepository.save(transaction);
		}
		return "Amount has been deposited";
	}

	@Override
	public List<Transactions> getAccountTransactionDetails(Integer accountNumber) {
		List<Transactions> transactions = transactionsRepository.findByAccountNumber(accountNumber);
		return transactions;
	}
}
