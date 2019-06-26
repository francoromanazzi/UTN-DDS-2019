package utils;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class MailSender {
	//Creé un mail nuevo de gmail para que sea el emisor de mails
	private static String SenderAccountEmail = "utnquemepongo@gmail.com";
	private static String SenderAccountPassword = "qmp123456";
	
	public static void send(String DestinyEmail, String subject, String text) throws AddressException, MessagingException {
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
		message.setFrom(new InternetAddress(SenderAccountEmail));
		message.setRecipient(Message.RecipientType.TO, new InternetAddress(DestinyEmail));
		message.setSubject(subject);
		message.setText(text);
		Transport.send(message);
	}
}
