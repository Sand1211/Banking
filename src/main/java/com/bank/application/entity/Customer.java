package com.bank.application.entity;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.bank.application.constants.Gender;
import com.sun.istack.NotNull;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "customer_info")
public class Customer {

	@Id
	@SequenceGenerator(name = "seq", initialValue = 10000)
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "seq")
	@Column(name = "cif_number")
	private Integer cifNumber;

	@NotNull
	@Column(name = "full_name")
	private String fullName;

	@NotNull
	@Column(name = "mobile_number")
	private String mobileNumber;

	@Column(name = "customer_email")
	private String email;

	@NotNull
	@Column(name = "gender")
	@Enumerated(EnumType.STRING)
	private Gender gender;

	@NotNull
	@Column(name = "user_name", unique = true)
	private String userName;

	@NotNull
	@Column(name = "password")
	private String password;

	@CreationTimestamp
	@Column(name = "registered_at")
	private LocalDateTime registeredAt;

	@UpdateTimestamp
	@Column(name = "updated_at")
	private LocalDateTime updatedAt;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "address_id")
	private Address address;

}
