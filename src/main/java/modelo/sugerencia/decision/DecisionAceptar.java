package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "ACEPTAR")
public class DecisionAceptar extends Decision {
	public DecisionAceptar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public DecisionAceptar() {
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.NUEVO);
	}
}
