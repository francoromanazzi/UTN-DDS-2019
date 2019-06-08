package modelo.pronosticos_del_clima;

import modelo.pronosticos_del_clima.clima.Clima;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

public class Pronostico {
	private final LocalDateTime fechaInicio, fechaFin;
	private final Clima clima;

	public Pronostico(LocalDateTime fechaInicio, LocalDateTime fechaFin, Clima clima) {
		this.fechaInicio = fechaInicio;
		this.fechaFin = fechaFin;
		this.clima = clima;
	}

	public LocalDateTime getFechaInicio() {
		return fechaInicio;
	}

	public LocalDateTime getFechaFin() {
		return fechaFin;
	}

	public Clima getClima() {
		return clima;
	}

	public boolean intervaloContieneAFecha(LocalDateTime fecha) {
		return !fecha.isBefore(fechaInicio) && !fecha.isAfter(fechaFin);
	}

	// A cuantos minutos del valor representativo está "fecha"
	// Se asume que "fecha" está en el intervalo
	long minutosHastaValorRepresentativo(LocalDateTime fecha) {
		long proximidadAInicio = Math.abs(ChronoUnit.MINUTES.between(fechaInicio, fecha));
		long puntoMedioPronostico = Math.abs(ChronoUnit.MINUTES.between(fechaInicio, fechaFin)) / 2;
		return Math.abs(puntoMedioPronostico - proximidadAInicio);
	}
}
