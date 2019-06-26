package modelo.sugerencia;

import modelo.parte_del_cuerpo.ParteDelCuerpo;

import java.util.Map;

public class CalificacionSugerencia {
	private final SensibilidadTemperatura sensibilidadGlobal;
	private final Map<ParteDelCuerpo, SensibilidadTemperatura> sensibilidadPorPartesDelCuerpo;

	public CalificacionSugerencia(SensibilidadTemperatura sensibilidadGlobal, Map<ParteDelCuerpo, SensibilidadTemperatura> sensibilidadPorPartesDelCuerpo) {
		this.sensibilidadGlobal = sensibilidadGlobal;
		this.sensibilidadPorPartesDelCuerpo = sensibilidadPorPartesDelCuerpo;
	}

	public SensibilidadTemperatura getSensibilidadGlobal() {
		return sensibilidadGlobal;
	}

	public Map<ParteDelCuerpo, SensibilidadTemperatura> getSensibilidadPorPartesDelCuerpo() {
		return sensibilidadPorPartesDelCuerpo;
	}
}
