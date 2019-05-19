package modelo.atuendo;

public class DecisionRechazar extends Decision {
	public DecisionRechazar(Atuendo atuendo) {
		super(atuendo);
	}

	public void deshacer() {
		getAtuendo().setEstado(EstadoAtuendo.NUEVO);
	}
}