package modelo.clima;

import modelo.prenda.NivelDeAbrigo;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.EnumSet;

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
		Collection<NivelDeAbrigo> ret;
		double celsius = temperatura.toCelsius();

		if(celsius < 5)
			ret = EnumSet.of(NivelDeAbrigo.MUCHO);
		else if(celsius < 15)
			ret = EnumSet.of(NivelDeAbrigo.MUCHO, NivelDeAbrigo.NORMAL);
		else if(celsius < 25)
			ret = EnumSet.of(NivelDeAbrigo.NORMAL, NivelDeAbrigo.POCO);
		else
			ret = EnumSet.of(NivelDeAbrigo.POCO);

		ret.add(NivelDeAbrigo.COMODIN);
		return ret;
	}
}
