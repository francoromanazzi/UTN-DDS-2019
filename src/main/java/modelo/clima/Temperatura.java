package modelo.clima;

import com.google.gson.annotations.SerializedName;

public class Temperatura {
	@SerializedName("Value")
	private double valor;

	@SerializedName("Unit")
	private String unidad;

	public double getValor() {
		return valor;
	}

	public String getUnidad() {
		return unidad;
	}
}
