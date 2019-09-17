package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;
import utils.MailSender;

import javax.mail.MessagingException;

public class EnviarMail implements MedioNotificacion {
	@Override
	public void send(Usuario usuario, AlertaMeteorologica alerta) throws MessagingException {
		MailSender.send(usuario.getMail(), "Alerta de " + alerta, "El servicio de Que Me Pongo le avisa que va a " + alerta);
	}
}