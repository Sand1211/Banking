package com.bank.application.entity;

import javax.persistence.Column;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetails {

	@Column(name = "msg_recipient")
	private String recipient;
	
	@Column(name = "email_Body")
	private String msgBody;
	
	@Column(name = "email_subject")
	private String subject;
	
	@Column(name = "mail_attachment")
	private String attachment;
}
