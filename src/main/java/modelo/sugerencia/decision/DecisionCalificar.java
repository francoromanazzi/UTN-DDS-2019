package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "CALIFICAR")
public class DecisionCalificar extends Decision {
	public DecisionCalificar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public DecisionCalificar() { }

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.ACEPTADO);
	}
}