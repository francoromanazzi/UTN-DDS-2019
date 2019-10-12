package modelo.evento;

import excepciones.FechaFinDebeSerPosteriorAFechaInicioException;
import excepciones.FrecuenciaDelEventoNula;
import excepciones.parametros_nulos.FechaFinNoPuedeSerNulaException;
import excepciones.parametros_nulos.FechaInicioNoPuedeSerNulaException;
import modelo.sugerencia.Sugerencia;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "eventos")
public class Evento {
	@Id
	@GeneratedValue
	private Long Id;
	private String titulo;
	private LocalDateTime fechaInicio, fechaFin;
	@Enumerated(EnumType.STRING)
	private FrecuenciaEvento frecuencia;
	@Enumerated(EnumType.STRING)
	private TipoEvento tipoEvento;
	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "evento_id")
	private final List<Sugerencia> sugerencias = new ArrayList<>();

	public Evento() {
	}

	public Evento(String titulo, LocalDateTime fechaInicio, LocalDateTime fechaFin, FrecuenciaEvento frecuencia, TipoEvento tipo) throws FechaFinDebeSerPosteriorAFechaInicioException, FechaInicioNoPuedeSerNulaException, FechaFinNoPuedeSerNulaException {
		if (fechaInicio == null) throw new FechaInicioNoPuedeSerNulaException();
		if (fechaFin == null) throw new FechaFinNoPuedeSerNulaException();
		if (fechaFin.isBefore(fechaInicio)) throw new FechaFinDebeSerPosteriorAFechaInicioException();
		if (frecuencia == null) throw new FrecuenciaDelEventoNula();

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

	@Override
	public boolean equals(Object o) {
		if (this == o) {
			return true;
		}
		if (o == null || getClass() != o.getClass()) {
			return false;
		}
		Evento otro = (Evento) o;
		return titulo.equals(otro.titulo) &&
				fechaInicio.isEqual(otro.fechaInicio) &&
				fechaFin.isEqual(otro.fechaFin) &&
				frecuencia == otro.frecuencia &&
				tipoEvento == otro.tipoEvento;
	}

	@Override
	public String toString() {
		return "Evento{" +
				"Id=" + Id +
				", titulo='" + titulo + '\'' +
				", fechaInicio=" + fechaInicio +
				", fechaFin=" + fechaFin +
				", frecuencia=" + frecuencia +
				", tipoEvento=" + tipoEvento +
				", sugerencias=" + sugerencias +
				'}';
	}
}
