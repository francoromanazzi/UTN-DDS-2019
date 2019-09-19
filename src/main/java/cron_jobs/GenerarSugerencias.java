package cron_jobs;

import excepciones.MensajeriaException;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import utils.MailSender;

import javax.mail.MessagingException;
import java.util.List;
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
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException, MensajeriaException {
		List<Sugerencia> sugerenciasGeneradas =
				guardarropaAUtilizar.generarSugerencias(evento, historialSugerencias);

		evento.addSugerencias(sugerenciasGeneradas);
		usuario.addToHistorialSugerencias(sugerenciasGeneradas);
		if (usuario.getEventos().contains(evento))
			notificarAUsuario();
	}

	private void notificarAUsuario() throws MensajeriaException {
		String texto = "Hola " + usuario.getNombre() + "!\n" + "Tus sugerencias para \"" + evento.getTitulo() +"\" ya estan listas!";
		MailSender.send(usuario.getMail(), "Sugerencias listas", texto);
	}
}
