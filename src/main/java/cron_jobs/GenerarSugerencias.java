package cron_jobs;

import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import utils.MailSender;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Map;
import java.util.TimerTask;

public class GenerarSugerencias extends TimerTask {
	private final Evento evento;
	private final Usuario usuario;
	private final Guardarropa guardarropaAUtilizar;
	private final List<Sugerencia> historialSugerencias;

	public GenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar, List<Sugerencia> historialSugerencias, Usuario user) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.historialSugerencias = historialSugerencias;
		this.usuario = user;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException {
		List<Sugerencia> sugerenciasGeneradas = 
				guardarropaAUtilizar.generarSugerencias(evento, historialSugerencias);
		
		
		evento.addSugerencias(sugerenciasGeneradas);
		usuario.addToHistorialSugerencias(sugerenciasGeneradas);
		//usuario.controlarTamanioHistorialSugerencias(); // Lo usamos ?
		if(usuario.getEventos().contains(evento))
			notificarAUsuario();
	}

	private void notificarAUsuario() {
		String texto = "Hola " + usuario.getNombre() + "!\n" + "Tus sugerencias ya estan listas!";

		// TODO hacer chequeada a esta excepcion
		try {
			MailSender.send(usuario.getMail(), "Sugerencias listas", texto);
		} catch(MessagingException e) { }
	}
}
