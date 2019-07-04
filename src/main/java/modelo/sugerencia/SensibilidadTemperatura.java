package modelo.sugerencia;

import java.util.List;

public enum SensibilidadTemperatura {
	FRIO, NORMAL, CALOR;

	public static SensibilidadTemperatura obtenerPromedioDeSensibilidad(List<SensibilidadTemperatura> sensibilidades) {
		long cantFrio = sensibilidades.stream().filter(sens -> sens == FRIO).count();
		long cantNormal = sensibilidades.stream().filter(sens -> sens == NORMAL).count();
		long cantCalor = sensibilidades.stream().filter(sens -> sens == CALOR).count();

		return cantFrio > cantNormal && cantFrio > cantCalor ? FRIO :
				cantCalor > cantNormal ? CALOR : NORMAL;
	}
}
