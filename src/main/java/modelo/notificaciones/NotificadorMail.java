package modelo.notificaciones;

import javax.mail.MessagingException;
import javax.mail.internet.AddressException;

import modelo.usuario.Usuario;
import utils.MailSender;

public class NotificadorMail implements Notificador{
	private static NotificadorMail instance = new NotificadorMail();
	
	private NotificadorMail() {}
	
	public static NotificadorMail getInstance() {
		return instance;
	}
	
	public void notificarSugerencias(Usuario usuario) {
		String texto = "Hola" + usuario.getNombre() + "!\n" + "Tus sugerencias ya están listas!";
		try {
			MailSender.send(usuario.getMail(), "Sugerencias listas", texto);
		} catch (AddressException e) {
			e.printStackTrace();
		} catch (MessagingException e) {
			e.printStackTrace();
		}
	}

	public void notificarAlerta(Usuario usuario) {
		
	}
}