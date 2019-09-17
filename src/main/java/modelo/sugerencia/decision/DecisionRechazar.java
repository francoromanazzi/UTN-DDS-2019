package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

import javax.persistence.DiscriminatorValue;
import javax.persistence.Entity;

@Entity
@DiscriminatorValue(value = "RECHAZAR")
public class DecisionRechazar extends Decision {
	public DecisionRechazar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public DecisionRechazar() {
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.NUEVO);
	}
}