package modelo.evento;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;

import java.time.LocalDateTime;
import java.util.Optional;

public class Evento {
	private final LocalDateTime fechaInicio, fechaFin;
	private final Optional<FrecuenciaEvento> frecuencia;
	private final TipoEvento tipoEvento;

	public Evento(LocalDateTime fechaInicio, LocalDateTime fechaFin, Optional<FrecuenciaEvento> frecuencia ,TipoEvento tipo) throws FechaFinDebeSerPosteriorAFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		if (fechaInicio == null) throw new FechaInicioNoPuedeSerNulaException();
		if (fechaFin == null) throw new FechaFinNoPuedeSerNulaException();
		if (fechaFin.isBefore(fechaInicio)) throw new FechaFinDebeSerPosteriorAFechaInicioException();

		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.frecuencia = frecuencia;
		this.tipoEvento = tipo;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}

}
