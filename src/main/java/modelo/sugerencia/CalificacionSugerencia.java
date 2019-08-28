package modelo.sugerencia;

import modelo.parte_del_cuerpo.ParteDelCuerpo;

import java.util.Map;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name="calificacion_sugerencias")
public class CalificacionSugerencia {
	@Id @GeneratedValue
	private Long Id;
	@Enumerated(EnumType.STRING)
	private SensibilidadTemperatura sensibilidadGlobal;
	@Transient
	private Map<ParteDelCuerpo, SensibilidadTemperatura> sensibilidadPorPartesDelCuerpo;

	public CalificacionSugerencia() {}
	
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
	
	public Long getId() {
		return this.Id;
	}
}
