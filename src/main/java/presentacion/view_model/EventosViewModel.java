package presentacion.view_model;
import modelo.evento.Evento;
import modelo.evento.FrecuenciaEvento;
import modelo.evento.TipoEvento;
import modelo.guardarropa.Guardarropa;
import modelo.usuario.Premium;
import modelo.usuario.Usuario;
import org.uqbar.commons.model.annotations.Observable;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Observable
public class EventosViewModel {
	private Usuario usuario;

	public EventosViewModel(Usuario usuario) {
		this.usuario = usuario;
		hardcodearUsuarioYEventos();
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public int getEventosSize() {
		return usuario.getEventosAgendados().size();
	}

	public List<EventoObservable> getEventos() {
		return usuario.getEventosAgendados().stream().map(ev ->
				new EventoObservable(ev.getTitulo(), ev.getFechaInicio(), ev.getFechaFin(), ev.getFrecuencia(), ev.getTipoEvento())
		).collect(Collectors.toList());
	}

	private void hardcodearUsuarioYEventos() {
		usuario.setPrivilegio(new Premium());

		Guardarropa guardarropa = new Guardarropa();
		usuario.addGuardarropa(guardarropa);

		Evento evento1 = new Evento("Salir a correr", LocalDateTime.now().plusSeconds(10), LocalDateTime.now().plusMinutes(5), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);
		Evento evento2 = new Evento("Ir a la facultad", LocalDateTime.now().plusSeconds(15), LocalDateTime.now().plusMinutes(5), FrecuenciaEvento.UNICA_VEZ, TipoEvento.INFORMAL);

		usuario.agendarEvento(evento1, guardarropa);
		usuario.agendarEvento(evento2, guardarropa);
	}
}
