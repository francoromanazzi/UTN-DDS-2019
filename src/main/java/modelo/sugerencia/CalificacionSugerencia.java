package modelo.sugerencia;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "calificaciones_sugerencia")
public class CalificacionSugerencia {
	@Id
	@GeneratedValue
	private Long Id;

	@Enumerated(EnumType.STRING)
	private SensibilidadTemperatura sensibilidadGlobal;

	@OneToMany(cascade = CascadeType.ALL)
	@JoinColumn(name = "calificacion_sugerencia_id")
	private final List<SensibilidadParteDelCuerpo> sensibilidadPorPartesDelCuerpo = new ArrayList<>();

	public CalificacionSugerencia() {
	}

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
