package modelo.sugerencia.decision;

import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

public class DecisionAceptar extends Decision {
	public DecisionAceptar(Sugerencia sugerencia) {
		super(sugerencia);
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.NUEVO);
	}
}
