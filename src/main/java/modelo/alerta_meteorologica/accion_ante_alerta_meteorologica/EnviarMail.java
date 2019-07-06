package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;
import utils.MailSender;

import javax.mail.MessagingException;

public class EnviarMail implements MedioNotificacion{
	MailSender mailSender = new MailSender();

	@Override
	public void send(Usuario usuario, AlertaMeteorologica alerta) throws MessagingException {
		mailSender.send(usuario.getMail(),"Alerta de " + alerta ,"El servicio de Que Me Pongo le avisa que va a " + alerta);
	}
}