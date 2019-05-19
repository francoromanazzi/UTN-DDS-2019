package modelo.clima;

public class Temperatura {
	private final double valor;
	private final String unidad;

	public Temperatura(double valor, String unidad) {
		this.valor = valor;
		this.unidad = unidad;
	}

	public double getValor() {
		return valor;
	}

	public String getUnidad() {
		return unidad;
	}

	public double toCelsius() {
		double ret;
		switch(unidad.toUpperCase()) {
			case "F":
				ret = (valor - 32) * 5/9;
				break;
			default:
				ret = valor;
		}
		return ret;
	}
}
