package com.bank.application.serviceImpl;

import java.util.List;
import java.util.Optional;

import javax.mail.MessagingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bank.application.dto.CustomerDetailsResponseDto;
import com.bank.application.entity.Accounts;
import com.bank.application.entity.Customer;
import com.bank.application.exceptions.BusinessException;
import com.bank.application.repository.AccountRepository;
import com.bank.application.repository.CustomerRepository;
import com.bank.application.service.CustomerService;
import com.bank.application.util.PasswordUtil;

@Service
//@Profile(value = {"dev","local","qa","prod"})
public class CustomerServiceImpl implements CustomerService {

	@Autowired
	CustomerRepository customerRepository;

	@Autowired
	AccountRepository accountRepository;

	@Autowired
	CustomerEmailServiceImpl emailService;

	@Override
	public Customer saveCustomerDetails(Customer customer) {
		generatePassword(customer);
		Customer customerResponse = customerRepository.save(customer);
		if (customerResponse == null) {
			return null;
		}
		try {
			emailService.sendCustomerMail(customer);
		} catch (MessagingException e) {
			e.printStackTrace();
		}
		return customerResponse;
	}

	@Override
	public CustomerDetailsResponseDto getCustomerDetails(Integer cifNumber) {
		Optional<Customer> customerResponse = customerRepository.findById(cifNumber);
		if (!customerResponse.isPresent()) {
			throw new BusinessException("Invalid cif number ");
		}
		List<Accounts> accountResponse = accountRepository.findByCifNumber(cifNumber);
		CustomerDetailsResponseDto customerDetailsResponseDto = new CustomerDetailsResponseDto();
		customerDetailsResponseDto.setCustomer(customerResponse.get());
		customerDetailsResponseDto.setAccount(accountResponse);
		customerDetailsResponseDto.setResponseMessage("SUCCESS");
		return customerDetailsResponseDto;
	}

	@Override
	public Customer updateCustomerDetails(Customer customer) {
		Customer save = null;
		Optional<Customer> findById = customerRepository.findById(customer.getCifNumber());
		if (findById == null) {
			throw new BusinessException("Customer does not exist with this ");
		} else if (findById.isPresent()) {
			Customer cust = new Customer();
			cust.setCifNumber(customer.getCifNumber());
			cust.setFullName(customer.getFullName());
			cust.setGender(customer.getGender());
			cust.setMobileNumber(customer.getMobileNumber());
			cust.setEmail(customer.getEmail());
			cust.setUserName(customer.getUserName());
			cust.setPassword(customer.getPassword());
			cust.setAddress(customer.getAddress());
			save = customerRepository.save(cust);
		}

		return save;
	}

	private void generatePassword(Customer customer) {
		String genPwd = PasswordUtil.generatePswd(8);
		customer.setPassword(genPwd);
	}

}
