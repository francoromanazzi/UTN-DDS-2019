package mocks;

import excepciones.MensajeriaException;
import excepciones.PronosticoNoDisponibleException;
import excepciones.SinSugerenciasPosiblesException;
import modelo.evento.Evento;
import modelo.guardarropa.Guardarropa;
import modelo.sugerencia.Sugerencia;
import modelo.usuario.Usuario;

import java.util.List;
import java.util.TimerTask;

public class GenerarSugerenciasNoPegarleALaDBNiNotificar extends TimerTask {
	private final Evento evento;
	private final Usuario user;
	private final Guardarropa guardarropaAUtilizar;

	public GenerarSugerenciasNoPegarleALaDBNiNotificar(Evento evento, Guardarropa guardarropaAUtilizar, Usuario user) {
		this.evento = evento;
		this.guardarropaAUtilizar = guardarropaAUtilizar;
		this.user = user;
	}

	@Override
	public void run() throws PronosticoNoDisponibleException, SinSugerenciasPosiblesException, MensajeriaException {
		// Verifico que el usuario no haya borrado el evento ni quitado el guardarropa
		if (!user.getEventos().contains(evento) || !guardarropaAUtilizar.tieneUsuario(user))
			return;

		List<Sugerencia> sugerenciasGeneradas =
				guardarropaAUtilizar.generarSugerencias(evento, user.getHistorialSugerencias());

		evento.addSugerencias(sugerenciasGeneradas);
		user.addToHistorialSugerencias(sugerenciasGeneradas);
	}
}
