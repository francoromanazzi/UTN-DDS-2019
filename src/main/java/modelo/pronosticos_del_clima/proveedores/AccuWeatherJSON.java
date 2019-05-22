package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.pronosticos_del_clima.Clima;
import modelo.pronosticos_del_clima.MeteorologoJSON;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.Temperatura;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class AccuWeatherJSON implements MeteorologoJSON {

	private class AccuWeatherTemperaturaJSON {
		@SerializedName("Value")
		private int valor;

		@SerializedName("Unit")
		private String unidad;
	}

	@SerializedName("DateTime")
	private String fecha;

	@SerializedName("Temperature")
	private AccuWeatherTemperaturaJSON temperatura;

	public Pronostico toPronostico() {
		LocalDateTime fechaParseada = LocalDateTime.parse(fecha, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		Temperatura temperaturaParseada = new Temperatura((double) temperatura.valor, temperatura.unidad);
		Clima clima = new Clima(temperaturaParseada);
		return new Pronostico(fechaParseada.minusMinutes(60), fechaParseada, clima);
		/*
		TODO En realidad el rango sería (0, 60) en vez de (-60, 0), pero accuweather no me devuelve a partir de la hora actual
		 sino a partir de la siguiente. Ej si son las 7:05, me devuelve (8:00, 9:00), (9:00, 10:00), etc, entonces les resto 1 hora
		*/
	}
}
