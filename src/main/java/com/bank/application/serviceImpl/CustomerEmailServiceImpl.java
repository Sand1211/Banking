package com.bank.application.serviceImpl;

import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import com.bank.application.entity.Customer;

import javax.mail.MessagingException;

@Service
public class CustomerEmailServiceImpl {

	private final TemplateEngine templateEngine;

	private final JavaMailSender javaMailSender;

	public CustomerEmailServiceImpl(TemplateEngine templateEngine, JavaMailSender javaMailSender) {
		this.templateEngine = templateEngine;
		this.javaMailSender = javaMailSender;
	}

	public void sendCustomerMail(Customer customer) throws MessagingException {
		Context context = new Context();
		context.setVariable("customer", customer);

		String process = templateEngine.process("emails/customer_regiser.html", context);
		javax.mail.internet.MimeMessage mimeMessage = javaMailSender.createMimeMessage();
		MimeMessageHelper helper = new MimeMessageHelper(mimeMessage);
		helper.setSubject("Welcome " + customer.getUserName());
		helper.setText(process, true);
		helper.setTo(customer.getEmail());
		javaMailSender.send(mimeMessage);
	}
}
