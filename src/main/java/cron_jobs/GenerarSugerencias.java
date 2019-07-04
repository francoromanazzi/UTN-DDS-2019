package cron_jobs;

import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.notificaciones.NotificadorMail;
import modelo.notificaciones.NotificadorSMS;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class GenerarSugerencias extends TimerTask {
	private final Evento evento;
	private final Usuario usuario;
	private final Guardarropa guardarropaAUtilizar;
	private final Map<Evento, List<Sugerencia>> sugerenciasParaEventos;
	private final List<Sugerencia> historialSugerencias;

	public GenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar, Map<Evento, List<Sugerencia>> sugerenciasParaEventos, List<Sugerencia> historialSugerencias, Usuario user) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.sugerenciasParaEventos = sugerenciasParaEventos;
		this.historialSugerencias = historialSugerencias;
		this.usuario = user;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		List<Sugerencia> sugerenciasGeneradas = guardarropaAUtilizar.generarSugerencias(evento, historialSugerencias);
		sugerenciasParaEventos.put(evento, sugerenciasGeneradas);
		NotificadorMail.getInstance().notificarSugerencias(this.usuario);
		NotificadorSMS.getInstance().notificarSugerencias(this.usuario);
	}
}
