package com.revature.organization.service;

import java.util.Date;
import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.revature.organization.model.Emailmessage;

@Service
public class NotificationSender {
	
	
	@Value("${spring.mail.username}")
	private String username;
	@Value("${spring.mail.password}")
	private String password;
	
	
	
	@Async
	public void sendNotification(Emailmessage emailMessage) throws MailException, InterruptedException, AddressException, MessagingException {

	//	System.out.println("Sleeping now...");
       // Thread.sleep(10000);

   //     System.out.println("Sending email...");

        Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", "smtp.gmail.com");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.port", "587");
		
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(username, password);
			}
		});
		Message msg = new MimeMessage(session);
		msg.setFrom(new InternetAddress(username, false));
		msg.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailMessage.getTo_address()));
		msg.setSubject(emailMessage.getSubject());
		msg.setContent("\r\n" + 
				"   <div\r\n" + 
				"    style=\"max-width:550px; min-width:320px;  background-color: #2D3445; border: 1px solid #DDDDDD; margin-right: auto; margin-left: auto;\">\r\n" + 
				"    <div style=\"margin-left:30px;margin-right:30px\">\r\n" + 
				"        <p>&nbsp;</p>\r\n" + 
				"        <p><a href=\"https://logico.com.ar\"\r\n" + 
				"                style=\"text-decoration:none;font-family:Verdana, Geneva, sans-serif;font-weight: bold; color: white;font-size: 15px\">Revature</a>\r\n" + 
				"        </p>\r\n" + 
				"        <hr style=\"margin-top:10px;margin-bottom:65px;border:none;border-bottom:3px solid red\" />\r\n" + 
				"        <h1\r\n" + 
				"            style=\"font-family: Tahoma, Geneva, sans-serif; font-weight: normal; color: white; text-align: center; margin-bottom: 65px;font-size: 20px; letter-spacing: 6px;font-weight: normal; border: 2px solid black; padding: 15px;\">\r\n" + 
				"            Welcome </h1>\r\n" + 
				"        <h3 style=\"font-family:Palatino Linotype, Book Antiqua, Palatino, serif;color: white ;font-style:italic;font-weight:500\">Your \r\n" + 
				"            Registration is <span style=\"border-bottom: 3px solid red\">Successful</span> </h3>\r\n" + 
				"        <p\r\n" + 
				"            style=\"font-family:Palatino Linotype, Book Antiqua, Palatino, serif;font-size: 15px; margin-left: auto; margin-right: auto; text-align: justify;color: white;line-height:1.5;margin-bottom:75px\">\r\n" + 
				"          "+emailMessage.getBody()+"\r\n" + 
				"            <br>\r\n" + 
				"            Please use the below link to activate your account</p>\r\n" + 
				"        <table style=\"width:100%;\">\r\n" + 
				"            <th>\r\n" + 
				"            <td style=\"width:25%\"></td>\r\n" + 
				"            <td style=\"background-color:black;width:50%;text-align:center;padding:15px\">\r\n" + 
				"                <a href=\"http://localhost:4200/\"\r\n" + 
				"                    style=\"margin-left: auto; margin-right: auto;text-decoration:none;color: white;text-align:center;font-family:Courier New, Courier, monospace;font-weight:600;letter-spacing:2px;background-color:black;padding:15px\">\r\n" + 
				"                    Click here</a></td>\r\n" + 
				"           \r\n" + 
				"            </th>\r\n" + 
				"        </table>\r\n" + 
				"        <hr style=\"margin-top:10px;margin-top:75px\" />\r\n" + 
				"        <p style=\"text-align:center;margin-bottom:15px\"><small\r\n" + 
				"                style=\"text-align:center;font-family:Courier New, Courier, monospace;font-size:10px;color:azure;\">\r\n" + 
				"                <a href=\"https://revature.in/\" style=\"color:white\">Revature</a> | Made with <span\r\n" + 
				"                    style=\"color:red\">&hearts;</span> </small></p>\r\n" + 
				"        <p>&nbsp;</p>\r\n" + 
				"    </div>\r\n" + 
				"", "text/html");
		msg.setSentDate(new Date());

		Transport.send(msg);
		

		System.out.println("Email Sent!");
	}
}


