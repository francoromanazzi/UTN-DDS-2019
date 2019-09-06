package modelo.evento;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.FrecuenciaDelEventoNula;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;
import modelo.sugerencia.Sugerencia;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Table;

@Entity
@Table(name="eventos")
public class Evento {
	@Id @GeneratedValue
	private Long Id;
	private String titulo;
	private LocalDateTime fechaInicio, fechaFin;
	@Enumerated(EnumType.STRING)
	private FrecuenciaEvento frecuencia;
	@Enumerated(EnumType.STRING)
	private TipoEvento tipoEvento;
	@OneToMany
	@JoinColumn(name="evento_id")
	private final List<Sugerencia> sugerencias = new ArrayList<Sugerencia>();

	

	public Evento() {} 
	
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

	public Long getId() {
		return this.Id;
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
	
	public List<Sugerencia> getSugerencias() {
		return this.sugerencias;
	}
	
	public void addSugerencia(Sugerencia sugerencia) {
		this.sugerencias.add(sugerencia);
	}
	
	public void addSugerencias(List<Sugerencia> sugerencias) {
		this.sugerencias.addAll(sugerencias);
	}
}
