package presentacion.view_model;

import modelo.evento.Evento;
import org.uqbar.commons.model.annotations.Observable;

@Observable
public class EventoObservable {
	private final Evento evento;
	private boolean sugerenciasFueronGeneradas;

	EventoObservable(Evento evento, boolean sugerenciasFueronGeneradas) {
		this.evento = evento;
		this.sugerenciasFueronGeneradas = sugerenciasFueronGeneradas;
	}

	public Evento getEvento() {
		return evento;
	}

	public boolean getSugerenciasFueronGeneradas() {
		return sugerenciasFueronGeneradas;
	}

	public void setSugerenciasFueronGeneradas(boolean sugerenciasFueronGeneradas) {
		this.sugerenciasFueronGeneradas = sugerenciasFueronGeneradas;
	}
}
