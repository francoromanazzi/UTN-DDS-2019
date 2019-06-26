package cron_jobs;

import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;

import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class GenerarSugerencias extends TimerTask {
	private final Evento evento;
	private final Guardarropa guardarropaAUtilizar;
	private final Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos;

	public GenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar, Map<Evento, List<Sugerencia>> sugerenciasGeneradasParaEventos) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.sugerenciasGeneradasParaEventos = sugerenciasGeneradasParaEventos;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		List<Sugerencia> sugerenciasGeneradas = guardarropaAUtilizar.generarSugerencias(evento);
		sugerenciasGeneradasParaEventos.put(evento, sugerenciasGeneradas);
	}
}
