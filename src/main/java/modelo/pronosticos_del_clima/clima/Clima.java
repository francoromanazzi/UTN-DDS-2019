package modelo.pronosticos_del_clima.clima;

import modelo.prenda.NivelDeAbrigo;
import modelo.pronosticos_del_clima.clima.temperatura.Temperatura;

import java.util.Collection;
import java.util.EnumSet;

public class Clima {
	private final Temperatura temperatura;

	public Clima(Temperatura temperatura) {
		this.temperatura = temperatura;
	}

	public Temperatura getTemperatura() {
		return temperatura;
	}

	public Collection<NivelDeAbrigo> formasDeAbrigarme() {
		Collection<NivelDeAbrigo> ret;
		double celsius = temperatura.toCelsius().getValor();

		//TODO comportamiento en el enum en vez de aca
		if(celsius < 5)
			ret = EnumSet.of(NivelDeAbrigo.MUCHO);
		else if(celsius < 15)
			ret = EnumSet.of(NivelDeAbrigo.MUCHO, NivelDeAbrigo.NORMAL);
		else if(celsius < 25)
			ret = EnumSet.of(NivelDeAbrigo.NORMAL, NivelDeAbrigo.POCO);
		else
			ret = EnumSet.of(NivelDeAbrigo.POCO);

		ret.add(NivelDeAbrigo.COMODIN);
		return ret;
	}
}
