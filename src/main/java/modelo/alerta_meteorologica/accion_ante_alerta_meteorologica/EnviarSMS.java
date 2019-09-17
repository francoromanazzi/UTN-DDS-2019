package modelo.alerta_meteorologica.accion_ante_alerta_meteorologica;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.usuario.Usuario;
import utils.SmsSender;

public class EnviarSMS implements MedioNotificacion {
	@Override
	public void send(Usuario usuario, AlertaMeteorologica alerta) {
		SmsSender.send(usuario.getNumeroTelefono(), "El servicio de Que Me Pongo le avisa que va a " + alerta);
	}
}
