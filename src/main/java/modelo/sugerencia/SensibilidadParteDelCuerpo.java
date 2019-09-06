package modelo.sugerencia;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import modelo.parte_del_cuerpo.ParteDelCuerpo;

@Entity
@Table(name = "sensibilidades_por_parte_del_cuerpo")
public class SensibilidadParteDelCuerpo {
	@Id @GeneratedValue
	private Long Id;
	
	@Enumerated(EnumType.STRING)
	private ParteDelCuerpo parteDelCuerpo; 
	@Enumerated(EnumType.STRING)
	private SensibilidadTemperatura sensibilidad;
	
	public SensibilidadParteDelCuerpo(ParteDelCuerpo parteDelCuerpo, SensibilidadTemperatura sensibilidad) {
		this.parteDelCuerpo = parteDelCuerpo;
		this.sensibilidad = sensibilidad;
	}
	
	public ParteDelCuerpo getParteDelCuerpo() {
		return parteDelCuerpo;
	}
	
	public void setParteDelCuerpo(ParteDelCuerpo parteDelCuerpo) {
		this.parteDelCuerpo = parteDelCuerpo;
	}
	
	public SensibilidadTemperatura getSensibilidad() {
		return sensibilidad;
	}
	
	public void setSensibilidad(SensibilidadTemperatura sensibilidad) {
		this.sensibilidad = sensibilidad;
	}

	public Long getId() {
		return Id;
	}
}
