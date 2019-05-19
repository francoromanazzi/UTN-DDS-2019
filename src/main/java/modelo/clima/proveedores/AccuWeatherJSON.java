package modelo.clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.clima.Clima;
import modelo.clima.MeteorologoJSON;
import modelo.clima.Temperatura;

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

	@SerializedName("PrecipitationProbability")
	private double probabilidadPrecipitacion;

	public Clima toClima() {
		LocalDateTime fechaParseada = LocalDateTime.parse(fecha, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
		Temperatura temperaturaParseada = new Temperatura((double) temperatura.valor, temperatura.unidad);
		return new Clima(fechaParseada, temperaturaParseada, probabilidadPrecipitacion / 100);
	}
}
