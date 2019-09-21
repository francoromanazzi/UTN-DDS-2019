package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.alerta_meteorologica.AlertaMeteorologica;
import modelo.pronosticos_del_clima.Pronostico;
import modelo.pronosticos_del_clima.clima.Clima;
import modelo.pronosticos_del_clima.clima.temperatura.Celsius;
import modelo.pronosticos_del_clima.clima.temperatura.Temperatura;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.TimeZone;

public class DarkSkyJSON implements PronosticadorClimaJSON, AlertadorMeteorologicoJSON {

	@SerializedName("time")
	private long fecha;

	@SerializedName("temperature")
	private double temperatura;

	@SerializedName("precipProbability")
	private double probabilidadPrecipitacion;

	@SerializedName("precipType")
	private String tipoPrecipitacion; // Puede ser null

	@Override
	public Pronostico toPronostico() {
		LocalDateTime fechaParseada = LocalDateTime.ofInstant(Instant.ofEpochSecond(fecha), TimeZone.getDefault().toZoneId());
		Temperatura temperaturaParseada = new Celsius(temperatura);
		Clima clima = new Clima(temperaturaParseada);
		return new Pronostico(fechaParseada, fechaParseada.plusMinutes(60), clima); // Documentación: "hourly data point objects are pointed to the top of the hour"
	}

	@Override
	public Optional<AlertaMeteorologica> toAlertaMeteorologica() {
		if(tipoPrecipitacion != null && probabilidadPrecipitacion > 0.5) {
			switch (tipoPrecipitacion) {
				case "rain":
					return Optional.of(AlertaMeteorologica.LLUVIA);
				case "snow":
					return Optional.of(AlertaMeteorologica.NIEVE);
				case "sleet":
					return Optional.of(AlertaMeteorologica.GRANIZO);
			}
		}
		return Optional.empty();
	}
}
