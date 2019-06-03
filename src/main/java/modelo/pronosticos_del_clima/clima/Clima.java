package modelo.pronosticos_del_clima.clima;

import modelo.pronosticos_del_clima.clima.temperatura.Temperatura;

public class Clima {
	private final Temperatura temperatura;

	public Clima(Temperatura temperatura) {
		this.temperatura = temperatura;
	}

	public Temperatura getTemperatura() {
		return temperatura;
	}
}
