package com.bank.application.entity;

import java.time.LocalDateTime;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bank.application.constants.AccountType;

import lombok.Data;

@Data
@Entity
public class Accounts {

	@Id
	@SequenceGenerator(name = "seq", initialValue = 5555)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "account_number")
	private Integer accountNumber;
	
	@Column(name = "account_balance")
	private float balanceAmount;
	
	@Column(name = "cif_number")
	private Integer cifNumber;
	
	@Column(name = "account_Status")
	private String accountStatus;
	
	@Enumerated(EnumType.STRING)
	@Column(name = "account_type")
	private AccountType accountType;

	@CreationTimestamp
	@Column(name = "created_at")
	private LocalDateTime createdAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	
}
