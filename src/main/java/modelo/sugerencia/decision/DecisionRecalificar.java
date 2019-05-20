package modelo.sugerencia.decision;

import modelo.sugerencia.CalificacionSugerencia;
import modelo.sugerencia.EstadoSugerencia;
import modelo.sugerencia.Sugerencia;

public class DecisionRecalificar extends Decision {
	private final CalificacionSugerencia calificacionAnterior;

	public DecisionRecalificar(Sugerencia sugerencia) {
		super(sugerencia);
		this.calificacionAnterior = sugerencia.getCalificacion();
	}

	public void deshacer() {
		getSugerencia().setEstado(EstadoSugerencia.CALIFICADO);
		getSugerencia().setCalificacion(calificacionAnterior);
	}
}