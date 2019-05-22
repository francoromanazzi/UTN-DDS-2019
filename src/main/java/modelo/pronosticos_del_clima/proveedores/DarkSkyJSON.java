package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.pronosticos_del_clima.Clima;
import modelo.pronosticos_del_clima.MeteorologoJSON;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.Temperatura;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.TimeZone;

public class DarkSkyJSON implements MeteorologoJSON {

	@SerializedName("time")
	private long fecha;

	@SerializedName("temperature")
	private double temperatura;

	public Pronostico toPronostico() {
		LocalDateTime fechaParseada = LocalDateTime.ofInstant(Instant.ofEpochSecond(fecha), TimeZone.getDefault().toZoneId());
		Temperatura temperaturaParseada = new Temperatura(temperatura, "C");
		Clima clima = new Clima(temperaturaParseada);
		return new Pronostico(fechaParseada, fechaParseada.plusMinutes(60), clima); // Documentación: "hourly data point objects are pointed to the top of the hour"
	}
}
