package modelo.evento;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;

import java.time.LocalDateTime;

public class Evento {
	private final LocalDateTime fechaInicio, fechaFin;
	private FrecuenciaEvento frecuencia;

	public Evento(LocalDateTime fechaInicio, LocalDateTime fechaFin) throws FechaFinDebeSerPosteriorAFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		if (fechaInicio == null) throw new FechaInicioNoPuedeSerNulaException();
		if (fechaFin == null) throw new FechaFinNoPuedeSerNulaException();
		if (fechaFin.isBefore(fechaInicio)) throw new FechaFinDebeSerPosteriorAFechaInicioException();

		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public FrecuenciaEvento getFrecuencia() {
		return frecuencia;
	}

	public void setFrecuencia(FrecuenciaEvento frecuencia) {
		this.frecuencia = frecuencia;
	}
}
