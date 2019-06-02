package modelo.pronosticos_del_clima.clima.temperatura;

public class Fahrenheit extends Temperatura {

	public Fahrenheit(double valor) {
		super(valor);
	}

	@Override
	public Temperatura toCelsius() {
		return new Celsius((this.valor - 32) * 5/9);
	}

	@Override
	public Temperatura toFahrenheit() {
		return this;
	}
}
