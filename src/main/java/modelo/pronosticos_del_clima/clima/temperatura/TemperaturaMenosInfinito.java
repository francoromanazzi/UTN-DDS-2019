package modelo.pronosticos_del_clima.clima.temperatura;

public class TemperaturaMenosInfinito extends Temperatura {

	public TemperaturaMenosInfinito() {
		super(Double.MIN_VALUE);
	}

	@Override
	public Temperatura toCelsius() {
		return new Celsius(Double.MIN_VALUE);
	}

	@Override
	public Temperatura toFahrenheit() {
		return new Fahrenheit(Double.MIN_VALUE);
	}
}
