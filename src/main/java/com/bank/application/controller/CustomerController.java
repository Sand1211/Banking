package com.bank.application.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.bank.application.dto.CustomerDetailsResponseDto;
import com.bank.application.dto.CustomerResponse;
import com.bank.application.entity.Customer;
import com.bank.application.service.CustomerService;

@RestController
@RequestMapping("/customer")
public class CustomerController {

	@Autowired
	CustomerService customerService;

	@PostMapping("/register")
	public ResponseEntity<CustomerResponse> customerRegister(@RequestBody Customer customer) {
		Customer response = customerService.saveCustomerDetails(customer);
		CustomerResponse customerResponse = new CustomerResponse();
		if (response == null) {
			customerResponse.setResponseMessage("Data is not Saved");
			customerResponse.setStatusCode("500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerResponse);
		}
		customerResponse.setCustomerResponse(response);
		customerResponse.setResponseMessage("Data is Saved Successfully");
		customerResponse.setStatusCode("200");
		return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
	}

	@GetMapping("/details/{cifNumber}")
	public ResponseEntity<CustomerDetailsResponseDto> getCustomerDetails(@PathVariable("cifNumber") Integer cifNumber) {
		CustomerDetailsResponseDto response = customerService.getCustomerDetails(cifNumber);
		return ResponseEntity.status(HttpStatus.OK).body(response);
	}
	
	@PutMapping("/update")
	public ResponseEntity<CustomerResponse> updateCustomer(@RequestBody Customer customer) {
		Customer response = customerService.updateCustomerDetails(customer);
		CustomerResponse customerResponse = new CustomerResponse();
		if (response == null) {
			customerResponse.setResponseMessage("Data is not Saved");
			customerResponse.setStatusCode("500");
			return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(customerResponse);
		}
		customerResponse.setCustomerResponse(response);
		customerResponse.setResponseMessage("Customer updated Successfully");
		customerResponse.setStatusCode("200");
		return ResponseEntity.status(HttpStatus.CREATED).body(customerResponse);
	}

}
