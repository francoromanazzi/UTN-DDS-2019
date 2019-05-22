package modelo.evento;

import excepciones.FechaFinDebeSerAntesQueFechaInicioException;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;

import java.time.LocalDateTime;

public abstract class Evento {
	private final LocalDateTime fechaInicio, fechaFin;

	Evento(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws FechaFinDebeSerAntesQueFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		if (fechaInicio == null)
			throw new FechaInicioNoPuedeSerNulaException();
		if (fechaFin == null)
			throw new FechaFinNoPuedeSerNulaException();
		if(fechaFin.isBefore(fechaInicio))
			throw new FechaFinDebeSerAntesQueFechaInicioException();

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
