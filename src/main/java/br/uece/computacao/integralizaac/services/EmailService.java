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

/**
 * @author Jocélio Otávio
 *
 * Classe responsável por enviar email utilizando o protocolo SMTP.
 * Os dados de acesso a conta do email via SMTP ficam no arquivo
 * mail-smtp.properties
 *
 */
public class EmailService {
	/**
	 * Tag que identifica todos os emails enviados pela aplicação.
	 */
	private static final String TAG_APP_EMAIL = "[IntegralizaAC]";
	
	/**
	 * Representa o arquivo properties com os dados SMTP. 
	 */
	private Properties smtpConfig;
	
	/**
	 * Construtor responsável por carregar o arquivo com os dados SMTP.
	 */
	public EmailService() {
		try {
			smtpConfig = new Properties();
			smtpConfig.load(this.getClass().getClassLoader().getResourceAsStream("mail-smtp.properties"));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Método que envia email.
	 * 
	 * @param email DTO com os dados do email que deve ser enviado.
	 */
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
