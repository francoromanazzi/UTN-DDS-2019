package modelo.evento;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.FrecuenciaDelEventoNula;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;
import java.time.LocalDateTime;

public class Evento {
	private final String titulo;
	private final LocalDateTime fechaInicio, fechaFin;
	private final FrecuenciaEvento frecuencia;
	private final TipoEvento tipoEvento;

	public Evento(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaEvento frecuencia ,TipoEvento tipo) throws FechaFinDebeSerPosteriorAFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		if (fechaInicio == null) throw new FechaInicioNoPuedeSerNulaException();
		if (fechaFin == null) throw new FechaFinNoPuedeSerNulaException();
		if (fechaFin.isBefore(fechaInicio)) throw new FechaFinDebeSerPosteriorAFechaInicioException();
		if(frecuencia == null) throw new FrecuenciaDelEventoNula();
		
		this.titulo = titulo;
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.frecuencia = frecuencia;
		this.tipoEvento = tipo;
	}

	public String getTitulo() {
		return titulo;
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

	public TipoEvento getTipoEvento() {
		return tipoEvento;
	}
}
