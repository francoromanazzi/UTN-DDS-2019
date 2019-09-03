package modelo.sugerencia;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name = "calificaciones_sugerencias")
public class CalificacionSugerencia {
	@Id @GeneratedValue
	private Long Id;
	
	@Enumerated(EnumType.STRING)
	private SensibilidadTemperatura sensibilidadGlobal;
	
	@OneToMany
	@JoinColumn(name = "calificacion_sugerencia_id")
	private final List<SensibilidadParteDelCuerpo> sensibilidadPorPartesDelCuerpo = new ArrayList<>();

	public CalificacionSugerencia() {}
	
	public CalificacionSugerencia(SensibilidadTemperatura sensibilidadGlobal) {
		this.sensibilidadGlobal = sensibilidadGlobal;
	}

	public SensibilidadTemperatura getSensibilidadGlobal() {
		return this.sensibilidadGlobal;
	}

	public List<SensibilidadParteDelCuerpo> getSensibilidadPorPartesDelCuerpo() {
		return this.sensibilidadPorPartesDelCuerpo;
	}
	
	public Long getId() {
		return this.Id;
	}
}
