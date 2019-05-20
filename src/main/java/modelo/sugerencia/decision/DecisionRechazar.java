package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

public class DecisionRechazar extends Decision {
	public DecisionRechazar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.NUEVO);
	}
}