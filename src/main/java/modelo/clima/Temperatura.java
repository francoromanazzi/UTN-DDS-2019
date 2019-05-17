package modelo.clima;

import com.google.gson.annotations.SerializedName;

public class Temperatura {
	@SerializedName("Value")
	private int valor;

	@SerializedName("Unit")
	private String unidad;

	public int getValor() {
		return valor;
	}

	public String getUnidad() {
		return unidad;
	}
}
