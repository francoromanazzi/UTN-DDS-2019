package modelo.pronosticos_del_clima.clima.temperatura;

public class Celsius extends Temperatura {

	public Celsius(double valor) {
		super(valor);
	}

	@Override
	public Temperatura toCelsius() {
		return this;
	}

	@Override
	public Temperatura toFahrenheit() {
		return new Fahrenheit(this.valor * 9/5 + 32);
	}
}
