package modelo.clima;

import com.google.gson.annotations.SerializedName;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Clima {
	@SerializedName("DateTime")
	private String fecha;

	@SerializedName("IsDaylight")
	private Boolean esDeDia;

	@SerializedName("Temperature")
	private Temperatura temperatura;

	@SerializedName("PrecipitationProbability")
	private double probabilidadPrecipitacion;

	public LocalDateTime getFecha() {
		return LocalDateTime.parse(fecha, DateTimeFormatter.ISO_OFFSET_DATE_TIME);
	}

	public Boolean getEsDeDia() {
		return esDeDia;
	}

	public Temperatura getTemperatura() {
		return temperatura;
	}

	public double getProbabilidadPrecipitacion() {
		return probabilidadPrecipitacion;
	}
}
