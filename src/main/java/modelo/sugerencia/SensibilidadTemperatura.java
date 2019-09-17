package modelo.sugerencia;

import java.util.List;

public enum SensibilidadTemperatura {
	FRIO(-8), NORMAL(0), CALOR(8);

	private final double modificadorCelcius;

	SensibilidadTemperatura(double modificadorCelcius) {
		this.modificadorCelcius = modificadorCelcius;
	}

	public double getModificadorCelcius() {
		return this.modificadorCelcius;
	}

	public static SensibilidadTemperatura obtenerPromedioDeSensibilidad(List<SensibilidadTemperatura> sensibilidades) {
		long cantFrio = sensibilidades.stream().filter(sens -> sens == FRIO).count();

		long cantNormal = sensibilidades.stream().filter(sens -> sens == NORMAL).count();

		long cantCalor = sensibilidades.stream().filter(sens -> sens == CALOR).count();


		return cantFrio > cantNormal && cantFrio > cantCalor ? FRIO :
				cantCalor > cantNormal ? CALOR : NORMAL;
	}
}
