package modelo.sugerencia;

import modelo.parte_del_cuerpo.ParteDelCuerpo;

import java.util.Map;

import javax.persistence.ElementCollection;
import javax.persistence.Embeddable;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MapKeyColumn;
import javax.persistence.MapKeyEnumerated;
import javax.persistence.MapKeyJoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

@Embeddable
public class CalificacionSugerencia {
//	@Id @GeneratedValue
//	private Long Id;
	
	@Enumerated(EnumType.STRING)
	private SensibilidadTemperatura sensibilidadGlobal;
	
	// Revisar
	@ElementCollection
	@MapKeyColumn(name = "parte_del_cuerpo")
	@MapKeyEnumerated(EnumType.STRING)
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
	
//	public Long getId() {
//		return this.Id;
//	}
}
