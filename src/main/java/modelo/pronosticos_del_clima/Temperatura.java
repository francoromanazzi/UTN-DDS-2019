package modelo.pronosticos_del_clima;

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
		return unidad.toUpperCase().equals("F") ? (valor - 32) * 5/9 : valor;
	}
}
