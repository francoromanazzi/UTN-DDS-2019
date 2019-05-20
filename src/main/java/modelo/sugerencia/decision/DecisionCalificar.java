package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

public class DecisionCalificar extends Decision {
	public DecisionCalificar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.ACEPTADO);
	}
}