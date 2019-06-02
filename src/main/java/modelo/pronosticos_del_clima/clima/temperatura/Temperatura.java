package modelo.pronosticos_del_clima.clima.temperatura;

public abstract class Temperatura {
	final double valor;

	Temperatura(double valor) {
		this.valor = valor;
	}

	public double getValor() {
		return valor;
	}

	public abstract Temperatura toCelsius();
	public abstract Temperatura toFahrenheit();
}
