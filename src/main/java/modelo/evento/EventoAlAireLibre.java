package modelo.evento;

import java.time.LocalDateTime;

public class EventoAlAireLibre extends Evento {
	public EventoAlAireLibre(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		super(fechaInicio, fechaFin);
	}
}
