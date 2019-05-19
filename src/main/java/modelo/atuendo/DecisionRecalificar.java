package modelo.atuendo;

public class DecisionRecalificar extends Decision {
	private final CalificacionAtuendo calificacionAnterior;

	public DecisionRecalificar(Atuendo atuendo) {
		super(atuendo);
		this.calificacionAnterior = atuendo.getCalificacion();
	}

	public void deshacer() {
		getAtuendo().setEstado(EstadoAtuendo.CALIFICADO);
		getAtuendo().setCalificacion(calificacionAnterior);
	}
}