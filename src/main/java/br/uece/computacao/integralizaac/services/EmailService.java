package br.uece.computacao.integralizaac.services;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import br.uece.computacao.integralizaac.dto.EmailDto;

public class EmailService {
	private static final String TAG_APP_EMAIL = "[IntegralizaAC]";
	
	private Properties smtpConfig;
	
	public EmailService() {
		try {
			smtpConfig = new Properties();
			smtpConfig.load(this.getClass().getClassLoader().getResourceAsStream("mail-smtp.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void enviarEmail(EmailDto email) {
		try {
			Session session = Session.getInstance(smtpConfig,
					new javax.mail.Authenticator() {
						protected PasswordAuthentication getPasswordAuthentication() {
							return new PasswordAuthentication(smtpConfig.getProperty("userName"),
									smtpConfig.getProperty("password"));
						}
					});

			MimeMessage msg = new MimeMessage(session);
			
			msg.setText(email.getCorpo(), "utf-8", "html");
			msg.setSubject(TAG_APP_EMAIL + " " + email.getAssunto());
			msg.setFrom(new InternetAddress("siac@uece.br"));
			msg.addRecipient(Message.RecipientType.TO,    new InternetAddress(email.getDestinatarios()));
			Transport.send(msg);
			
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}
	
}
