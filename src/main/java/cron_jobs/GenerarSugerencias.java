package cron_jobs;

import excepciones.MensajeriaException;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import repositorios.RepositorioUsuarios;
import utils.MailSender;

import java.util.List;
import java.util.TimerTask;

public class GenerarSugerencias extends TimerTask {
	private final Evento evento;
	private final Long user_id;
	private final Guardarropa guardarropaAUtilizar;

	public GenerarSugerencias(Evento evento, Guardarropa guardarropaAUtilizar, Long user_id) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.user_id = user_id;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException, MensajeriaException {
		Usuario user = new RepositorioUsuarios().getUsuarioById(this.user_id);

		// Verifico que el usuario no haya borrado el evento ni quitado el guardarropa
		if (!user.getEventos().contains(evento) || !guardarropaAUtilizar.tieneUsuario(user))
			return;

		List<Sugerencia> sugerenciasGeneradas =
				guardarropaAUtilizar.generarSugerencias(evento, user.getHistorialSugerencias());

		evento.addSugerencias(sugerenciasGeneradas);
		user.addToHistorialSugerencias(sugerenciasGeneradas);
		notificarAUsuario(user);
	}

	private void notificarAUsuario(Usuario user) throws MensajeriaException {
		String texto = "Hola " + user.getNombre() + "!\n" + "Tus sugerencias para \"" + evento.getTitulo() + "\" ya estan listas!";
		MailSender.send(user.getMail(), "Sugerencias listas", texto);
	}
}
