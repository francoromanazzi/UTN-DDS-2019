package modelo.clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.clima.Clima;
import modelo.clima.MeteorologoJSON;
import modelo.clima.Temperatura;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DarkSkyJSON implements MeteorologoJSON {

	@SerializedName("time")
	private long fecha;

	@SerializedName("temperature")
	private double temperatura;

	@SerializedName("precipProbability")
	private double probabilidadPrecipitacion;

	public Clima toClima() {
		LocalDateTime fechaParseada = LocalDateTime.ofInstant(Instant.ofEpochSecond(fecha), TimeZone.getDefault().toZoneId());
		Temperatura temperaturaParseada = new Temperatura(temperatura, "C");
		return new Clima(fechaParseada, temperaturaParseada, probabilidadPrecipitacion);
	}
}
