package modelo.pronosticos_del_clima.clima.temperatura;

public class TemperaturaMasInfinito extends Temperatura {

	public TemperaturaMasInfinito() {
		super(Double.MAX_VALUE);
	}

	@Override
	public Temperatura toCelsius() {
		return new Celsius(Double.MAX_VALUE);
	}

	@Override
	public Temperatura toFahrenheit() {
		return new Fahrenheit(Double.MAX_VALUE);
	}
}
