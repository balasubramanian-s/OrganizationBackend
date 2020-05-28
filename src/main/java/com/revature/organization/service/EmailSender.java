package com.revature.organization.service;

import java.util.Collections;
import java.util.List;
import java.util.Properties;

import javax.mail.MessagingException;
import javax.mail.Session;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.security.GeneralSecurityException;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.Base64;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.gmail.Gmail;
import com.google.api.services.gmail.GmailScopes;
import com.google.api.services.gmail.model.Message;
import com.revature.organization.model.Emailmessage;
@Service
public class EmailSender {
	private static final String APPLICATION_NAME = "Organization";
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	private static final String TOKENS_DIRECTORY_PATH = "tokens/Google";

	private static final List<String> SCOPES = Collections.singletonList(GmailScopes.MAIL_GOOGLE_COM);
	private static final String CREDENTIALS_FILE_PATH = "/credentials.json";
	private static Message response;

	private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT) throws IOException {
		// Load client secrets.
		InputStream in = EmailSender.class.getResourceAsStream(CREDENTIALS_FILE_PATH);
		if (in == null) {
			throw new FileNotFoundException("Resource not found: " + CREDENTIALS_FILE_PATH);
		}
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));

		// Build flow and trigger user authorization request.
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, SCOPES)
						.setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKENS_DIRECTORY_PATH)))
						.setAccessType("offline").build();
		LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
		return new AuthorizationCodeInstalledApp(flow, receiver).authorize("user");
	}
	public static String sendEmail(Emailmessage msg) throws GeneralSecurityException, IOException, MessagingException {
		 // Build a new authorized API client service.
		
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Gmail service = new Gmail.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT))
                .setApplicationName(APPLICATION_NAME)
                .build();
        try {
        	
       
        
		String recipients=msg.getTo_address();
		
		String template=	"   <div\r\n" + 
				"    style=\"max-width:550px; min-width:320px;  background-color: #2D3445; border: 1px solid #DDDDDD; margin-right: auto; margin-left: auto;\">\r\n" + 
				"    <div style=\"margin-left:30px;margin-right:30px\">\r\n" + 
				"        <p>&nbsp;</p>\r\n" + 
				"        <p><a href=\"https://logico.com.ar\"\r\n" + 
				"                style=\"text-decoration:none;font-family:Verdana, Geneva, sans-serif;font-weight: bold; color: white;font-size: 15px\">Revature</a>\r\n" + 
				"        </p>\r\n" + 
				"        <hr style=\"margin-top:10px;margin-bottom:65px;border:none;border-bottom:3px solid red\" />\r\n" + 
				"        <h1\r\n" + 
				"            style=\"font-family: Tahoma, Geneva, sans-serif; font-weight: normal; color: white; text-align: center; margin-bottom: 65px;font-size: 20px; letter-spacing: 6px;font-weight: normal; border: 2px solid black; padding: 15px;\">\r\n" + 
				"            Welcome " + msg.getName()+",</h1>\r\n" + 
				"        <h3 style=\"font-family:Palatino Linotype, Book Antiqua, Palatino, serif;color: white ;font-style:italic;font-weight:500\">Your \r\n" + 
				"            Registration is <span style=\"border-bottom: 3px solid red\">Successful</span> </h3>\r\n" + 
				"        <p\r\n" + 
				"            style=\"font-family:Palatino Linotype, Book Antiqua, Palatino, serif;font-size: 15px; margin-left: auto; margin-right: auto; text-align: justify;color: white;line-height:1.5;margin-bottom:75px\">\r\n" + 
				"          "+msg.getBody()+"\r\n" + 
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
				"";

		 response=sendMessage(service,"me",createEmail(recipients,"me",msg.getSubject(),template));
		return response.getLabelIds().toString();
        }catch (Exception e) {
        	
        	return "Something went Wrong...Please Try again";
		}
		
	}
	private static Message sendMessage(Gmail service, String userId, MimeMessage emailContent)
			throws MessagingException, IOException {
		Message message = createMessageWithEmail(emailContent);
		return service.users().messages().send(userId, message).execute();

	
	}
	
	private static Message createMessageWithEmail(MimeMessage emailContent) throws MessagingException, IOException {
		ByteArrayOutputStream buffer = new ByteArrayOutputStream();
		emailContent.writeTo(buffer);
		byte[] bytes = buffer.toByteArray();
		String encodedEmail = Base64.encodeBase64URLSafeString(bytes);
		Message message = new Message();
		message.setRaw(encodedEmail);
		return message;
	}
	
	private static MimeMessage createEmail(String to, String from, String subject, String bodyText)
			throws MessagingException {
		Properties props = new Properties();
		Session session = Session.getDefaultInstance(props, null);
		MimeMessage email = new MimeMessage(session);			
		email.setFrom(new InternetAddress(from));
		email.addRecipients(javax.mail.Message.RecipientType.TO,InternetAddress.parse(to));
		email.setSubject(subject);
		email.setContent(bodyText, "text/html");
		return email;
	}


}
