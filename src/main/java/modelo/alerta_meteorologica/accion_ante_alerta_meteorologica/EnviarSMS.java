package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;
import utils.SmsSender;

import javax.mail.MessagingException;

public class EnviarSMS implements MedioNotificacion {
	SmsSender smsSender = new SmsSender();

	@Override
	public void send(Usuario usuario, AlertaMeteorologica alerta) throws MessagingException {
		smsSender.send(usuario.getNumeroTelefono(),"El servicio de Que Me Pongo le avisa que va a " + alerta);
	}
}
