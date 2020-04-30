package com.revature.organization.controller;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.MailException;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.revature.organization.exception.DBException;
import com.revature.organization.exception.HttpStatusResponse;
import com.revature.organization.model.AuthenticationRequest;
import com.revature.organization.model.AuthenticationResponse;
import com.revature.organization.model.Emailmessage;
import com.revature.organization.model.User;
import com.revature.organization.service.JwtUtil;
import com.revature.organization.service.MyUserDetailsService;
import com.revature.organization.service.NotificationSender;

@RestController
@CrossOrigin(origins = "*")
public class Authentication {

	@Autowired
	private AuthenticationManager authenticationManager;

	@Autowired
	private UserDetailsService userDetailsService;
	@Autowired
	private JwtUtil jwtUtil;
	@Autowired
	private MyUserDetailsService myUserDetailsService;
	@Autowired
	private NotificationSender notificationService;
	
	private Emailmessage message = new Emailmessage();
	
	
	@PostMapping({ "/authenticate" })
	public ResponseEntity<HttpStatusResponse> createAuthenticateToken(@RequestBody AuthenticationRequest authenticationRequest)
			throws BadCredentialsException, DBException {
		
			String hashed=myUserDetailsService.getEncryptedPassword(authenticationRequest.getUsername());
		//	System.out.println(hashed);
		//	System.out.println(authenticationRequest.getPassword());
		//	System.out.println(BCrypt.checkpw(authenticationRequest.getPassword(),hashed ));
			if(BCrypt.checkpw(authenticationRequest.getPassword(),hashed )) {
				authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(authenticationRequest.getUsername(),authenticationRequest.getPassword() ));
				final UserDetails userDetails = userDetailsService.loadUserByUsername(authenticationRequest.getUsername());				
				String jwt = jwtUtil.generateToken(userDetails);
				return new ResponseEntity<HttpStatusResponse> (new HttpStatusResponse(HttpStatus.OK.value(),"Credentials Verified",new AuthenticationResponse(jwt)),HttpStatus.OK);
			} 
			else {			
			
			return new ResponseEntity<HttpStatusResponse> (
					new HttpStatusResponse(HttpStatus.BAD_REQUEST.value(),"Invalid Username or Password ",""),HttpStatus.BAD_REQUEST);
		}
		
	}
	@PostMapping({ "/register" })
	public String signup(@Valid @RequestBody User user) throws AddressException, MessagingException, MailException, InterruptedException {
		
		user.setPassword( BCrypt.hashpw(user.getPassword(),BCrypt.gensalt()));
		//System.out.println(user.getPassword());
		
		
		myUserDetailsService.SaveUser(user);	
		message.setName(user.getFullname());
		message.setTo_address(user.getUsername());
		message.setSubject("Registration Successfull");
		message.setBody("Congratulations! <br>Your request for access to Revature Online has been approved. "
				+ "<br>	You are one step closer to launching an exciting career in technology.");
		notificationService.sendNotification(message);
		return "Registration Successfull";
		
	}
	
		
}
