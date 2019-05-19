package modelo.evento;

import java.time.LocalDateTime;

public class EventoEnInterior extends Evento {
	public EventoEnInterior(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		super(fechaInicio, fechaFin);
	}
}
