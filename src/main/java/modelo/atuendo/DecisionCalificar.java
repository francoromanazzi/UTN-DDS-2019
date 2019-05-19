package modelo.atuendo;

public class DecisionCalificar extends Decision {
	public DecisionCalificar(Atuendo atuendo) {
		super(atuendo);
	}

	public void deshacer() {
		getAtuendo().setEstado(EstadoAtuendo.ACEPTADO);
	}
}