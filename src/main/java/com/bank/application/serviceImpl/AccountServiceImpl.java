package com.bank.application.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.constants.AccountType;
import com.bank.application.dto.AccountCreationDto;
import com.bank.application.dto.AccountCreationResponseDto;
import com.bank.application.entity.Accounts;
import com.bank.application.entity.Customer;
import com.bank.application.exceptions.BusinessException;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.CustomerRepository;
import com.bank.application.service.AccountService;

@Service
@Transactional
public class AccountServiceImpl implements AccountService {

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerRepository customerRepository;

	@Override
	public Accounts createAccount(Accounts accountCreationDto) {
		Accounts accounts = new Accounts();
		Accounts response = null;
		Integer cifNumber = accountCreationDto.getCifNumber();
		AccountType accountType = accountCreationDto.getAccountType();
		Optional<Customer> customerResponse = customerRepository.findById(cifNumber);
		if (!customerResponse.isPresent()) {
			throw new BusinessException("Customer does not exists with this " + cifNumber + " number");
		} else {
			List<Accounts> accountList = accountRepository.getAccountList(cifNumber);
			if (accountList == null) {
				accounts.setAccountStatus("ACTIVE");
				accounts.setAccountType(accountCreationDto.getAccountType());
				accounts.setBalanceAmount(accountCreationDto.getBalanceAmount());
				accounts.setCifNumber(accountCreationDto.getCifNumber());
				response = accountRepository.save(accounts);

			} else {
				for (Accounts accnt : accountList) {
					if (accnt.getAccountType().equals(accountType)) {
						throw new BusinessException("Account already exist with " + accountType);
					}
				}
			}
		}
		accounts.setAccountStatus("ACTIVE");
		accounts.setAccountType(accountCreationDto.getAccountType());
		accounts.setBalanceAmount(accountCreationDto.getBalanceAmount());
		accounts.setCifNumber(accountCreationDto.getCifNumber());
		response = accountRepository.save(accounts);
		return response;
	}

	@SuppressWarnings("null")
	public AccountCreationResponseDto updateAccount(AccountCreationDto accountCreationDto) {
		Optional<Accounts> cifNumber = accountRepository.findById(accountCreationDto.getCifNumber());
		AccountCreationResponseDto accountResponse = null;
		if (cifNumber.isEmpty()) {
			throw new BusinessException("Account Number does not exists number");
		}
		Accounts accounts = new Accounts();
		accounts.setAccountStatus("ACTIVE");
		accounts.setAccountType(accountCreationDto.getAccountType());
		accounts.setBalanceAmount(accountCreationDto.getBalanceAmount());
		accounts.setCifNumber(accountCreationDto.getCifNumber());
		Accounts response = accountRepository.save(accounts);
		if (response == null) {
			accountResponse.setResponseMessage("Account Creation Failed");
			accountResponse.setStatusCode("401");
			return accountResponse;
		}
		accountResponse.setResponseMessage("Account Creation Successfull");
		accountResponse.setStatusCode("200");
		return accountResponse;
	}

	@Override
	public List<Accounts> getAccountDetails() {
		List<Accounts> accountList = accountRepository.findAll();
		return accountList;
	}

	@Override
	public Accounts getAccountById(Integer accountNumber) {
		Optional<Accounts> cifNumber = accountRepository.findById(accountNumber);
		if (cifNumber.isEmpty()) {
			throw new BusinessException("Account Number does not exists with this " + accountNumber);
		}
		return cifNumber.get();
	}

	public String deleteAccount(Integer accountNumber) {
		Optional<Accounts> accNo = accountRepository.findById(accountNumber);
		if (accNo.isEmpty()) {
			throw new BusinessException("Account Number does not exists with this " + accountNumber);
		} else if (accNo.get().getAccountStatus().equals("INACTIVE")) {
			throw new BusinessException("Account is not active with this " + accNo.get());
		} else {
			accountRepository.InactiveAccountByAccntNumber(accountNumber);
		}

		return "Account deleted successfully";
	}

	@Override
	public Customer customerExists(Integer cifNumber) {
		Optional<Customer> customerResponse = customerRepository.findById(cifNumber);
		if (!customerResponse.isPresent()) {
			throw new BusinessException("Customer does not exists with this " + cifNumber + " number");
		}
		return customerResponse.get();
	}

	@Override
	public void activateAccount(Integer accountNumber) {

		Optional<Accounts> findById = accountRepository.findById(accountNumber);

		if (findById.get().getAccountStatus().equals("INACTIVE")) {
			 accountRepository.activeAccountByAccntNumber(accountNumber);
			 
		}
	}

}
