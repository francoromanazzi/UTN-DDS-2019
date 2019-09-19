package cron_jobs;

import excepciones.MensajeriaException;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.usuario.Usuario;
import servicios.UsuarioService;

import javax.mail.MessagingException;
import java.util.List;
import java.util.TimerTask;

public class EscucharAlertasMeteorologicas extends TimerTask {

	@Override
	public void run() throws MensajeriaException {
		List<AlertaMeteorologica> alertas = ServicioDelClima.getInstance().obtenerAlertasMeteorologicas();
		List<Usuario> usuarios = new UsuarioService().GetAllUsuarios();

		alertas.forEach(alerta -> usuarios.forEach(user -> user.recibirAlertaMeteorologica(alerta)));
	}
}
