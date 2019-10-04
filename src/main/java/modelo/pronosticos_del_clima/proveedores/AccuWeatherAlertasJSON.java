package modelo.pronosticos_del_clima.proveedores;

import com.google.gson.annotations.SerializedName;
import modelo.alerta_meteorologica.AlertaMeteorologica;

import java.util.Optional;

public class AccuWeatherAlertasJSON implements AlertadorMeteorologicoJSON {
	@SerializedName("HasPrecipitation")
	private boolean hayPrecipitacion;

	@SerializedName("PrecipitationType")
	private String tipoPrecipitacion;

	@Override
	public Optional<AlertaMeteorologica> toAlertaMeteorologica() {
		if(hayPrecipitacion) {
			switch(tipoPrecipitacion) {
				case "Rain":
					return Optional.of(AlertaMeteorologica.LLUVIA);
				case "Snow":
					return Optional.of(AlertaMeteorologica.NIEVE);
				case "Ice":
				case "Mixed":
					return Optional.of(AlertaMeteorologica.GRANIZO);
			}
		}
		return Optional.empty();
	}
}
