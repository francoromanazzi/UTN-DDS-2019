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
		return new Pronostico(fechaParseada.minusMinutes(30), fechaParseada.plusMinutes(30), clima); // TODO No hardcodear acá el +-30, sino deducirlo por la respuesta de accuweather
	}
}
