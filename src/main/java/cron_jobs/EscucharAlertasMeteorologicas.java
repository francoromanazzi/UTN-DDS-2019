package cron_jobs;

import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.ServicioDelClima;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;

import java.util.List;
import java.util.TimerTask;

public class EscucharAlertasMeteorologicas extends TimerTask {
	@Override
	public void run() {
		List<AlertaMeteorologica> alertas = ServicioDelClima.getInstance().obtenerAlertasMeteorologicas();
		List<Usuario> usuarios = RepositorioUsuarios.getInstance().getUsuarios(); // Esto habria que sacarlo y usar hibernate para obtener a todos los usuarios
		alertas.forEach(alerta -> usuarios.forEach(user -> user.recibirAlertaMeteorologica(alerta)));
	}
}
