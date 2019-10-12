package cron_jobs;

import excepciones.*;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;
import repositorios.RepositorioEventos;
import repositorios.RepositorioGuardarropas;
import repositorios.RepositorioUsuarios;
import utils.MailSender;

import java.util.List;
import java.util.TimerTask;

public class GenerarSugerencias extends TimerTask {
	private final long evento_id, guardarropa_id, user_id;

	public GenerarSugerencias(long evento_id, long guardarropa_id, long user_id) {
		System.out.println("GenerarSugerencias");
		System.out.println(evento_id);
		System.out.println(guardarropa_id);
		System.out.println(user_id);

		this.evento_id = evento_id;
		this.guardarropa_id = guardarropa_id;
		this.user_id = user_id;
	}

	@Override
	public void run() throws UsuarioNoEncontradoException, GuardarropaNoEncontradoException, EventoNoEncontradoException, PronosticoNoDisponibleException, SinSugerenciasPosiblesException, MensajeriaException {
		Usuario user = new RepositorioUsuarios().getUsuarioById(this.user_id);
		Guardarropa guardarropa = new RepositorioGuardarropas().buscarPorId(this.guardarropa_id);
		Evento evento = new RepositorioEventos().buscarPorId(this.evento_id);

		// Verifico que el usuario no haya borrado el evento ni quitado el guardarropa
		if (!user.getEventos().contains(evento) || !guardarropa.tieneUsuario(user)) {
			System.out.println("RETORNANDO, " + !user.getEventos().contains(evento) + ", " + !guardarropa.tieneUsuario(user));
			System.out.println(evento);
			System.out.println(user.getEventos());
			return;
		}

		List<Sugerencia> sugerenciasGeneradas =
				guardarropa.generarSugerencias(evento, user.getHistorialSugerencias());

		System.out.println("SUGERENCIAS GENERADAS:");
		System.out.println(sugerenciasGeneradas);

		evento.addSugerencias(sugerenciasGeneradas);
		user.addToHistorialSugerencias(sugerenciasGeneradas);
		notificarAUsuario(user, evento);
	}

	private void notificarAUsuario(Usuario user, Evento evento) throws MensajeriaException {
		String texto = "Hola " + user.getNombre() + "!\n" + "Tus sugerencias para \"" + evento.getTitulo() + "\" ya estan listas!";
		MailSender.send(user.getMail(), "Sugerencias listas", texto);
	}
}
