package modelo.evento;

import java.time.LocalDateTime;

public abstract class Evento {
	private final LocalDateTime fechaInicio, fechaFin;

	public Evento(LocalDateTime fechaInicio, LocalDateTime fechaFin) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}
}
