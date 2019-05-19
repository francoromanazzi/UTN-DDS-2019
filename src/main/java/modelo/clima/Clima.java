package modelo.clima;

import modelo.prenda.NivelDeAbrigo;

import java.time.LocalDateTime;
import java.util.*;

public class Clima {
	private final LocalDateTime fecha;
	private final Temperatura temperatura;
	private final double probabilidadPrecipitacion;

	public Clima(LocalDateTime fecha, Temperatura temperatura, double probabilidadPrecipitacion) {
		this.fecha = fecha;
		this.temperatura = temperatura;
		this.probabilidadPrecipitacion = probabilidadPrecipitacion;
	}

	public LocalDateTime getFecha() {
		return fecha;
	}

	public Temperatura getTemperatura() {
		return temperatura;
	}

	public double getProbabilidadPrecipitacion() {
		return probabilidadPrecipitacion;
	}

	public Collection<NivelDeAbrigo> formasDeAbrigarme() {
		double celsius = temperatura.toCelsius();
		if(celsius < 5)
			return EnumSet.of(NivelDeAbrigo.MUCHO);
		else if(celsius < 15)
			return EnumSet.of(NivelDeAbrigo.MUCHO, NivelDeAbrigo.NORMAL);
		else if(celsius < 20)
			return EnumSet.of(NivelDeAbrigo.NORMAL, NivelDeAbrigo.POCO);
		else if(celsius < 30)
			return EnumSet.of(NivelDeAbrigo.POCO, NivelDeAbrigo.NADA);
		else
			return EnumSet.of(NivelDeAbrigo.NADA);
	}
}
