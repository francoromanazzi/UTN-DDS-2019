package utils;

import excepciones.MensajeriaException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public final class MailSender {
	private static final String SenderAccountEmail = "utnquemepongo@gmail.com";
	private static final String SenderAccountPassword = "qmp123456";

	public static void send(String destinyEmail, String subject, String text) throws MensajeriaException {
		Properties prop = new Properties();
		prop.put("mail.smtp.auth", "true");
		prop.put("mail.smtp.starttls.enable", "true");
		prop.put("mail.smtp.host", "smtp.gmail.com");
		prop.put("mail.smtp.port", "587");

		Session session = Session.getInstance(prop, new Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(SenderAccountEmail, SenderAccountPassword);
			}
		});

		Message message = new MimeMessage(session);

		try {
			message.setFrom(new InternetAddress(SenderAccountEmail));
			message.setRecipient(Message.RecipientType.TO, new InternetAddress(destinyEmail));
			message.setSubject(subject);
			message.setText(text);
			Transport.send(message);
		} catch (Exception e) {
			throw new MensajeriaException();
		}
	}
}
