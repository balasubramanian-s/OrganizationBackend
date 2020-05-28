package com.revature.organization.controller;

import java.io.IOException;
import java.security.GeneralSecurityException;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.MailException;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.model.Emailmessage;
import com.revature.organization.service.EmailSender;
import com.revature.organization.service.NotificationSender;
@RestController
@CrossOrigin(origins = "*")
public class EmailController {
	
	@Autowired
	private NotificationSender notificationService;
	@Autowired
	private EmailSender emailSender;
	
	
	@PostMapping({"/send"})
	@PreAuthorize("hasRole('ADMIN')")
	public String sendEmail(@RequestBody Emailmessage emailMessage) throws MessagingException,AddressException, MailException, InterruptedException, GeneralSecurityException, IOException {		
		//notificationService.sendNotification(emailMessage);
		emailSender.sendEmail(emailMessage);
		return "Email Sent Successfully";
	}
	
	

}
